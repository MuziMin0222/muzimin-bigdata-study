package com.lhm.copyOnWrite

import com.lhm.copyOnWrite.bean.TemperatureBean
import org.apache.hudi.DataSourceReadOptions._
import org.apache.hudi.DataSourceWriteOptions._
import org.apache.hudi.common.model.EmptyHoodieRecordPayload
import org.apache.hudi.config.HoodieIndexConfig._
import org.apache.hudi.config.HoodieWriteConfig._
import org.apache.hudi.index.HoodieIndex
import org.apache.spark.sql.SaveMode._
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-03-22 18:46
 *       基于COW表的LocalFS/HDFS使用
 **/
object Demo_01 {
  private val APP_NAME = Demo_01.getClass.getSimpleName
  private val MASTER = "local[*]"
  val SOURCE = "hudi"

  val insertData = Array[TemperatureBean](
    TemperatureBean("4301", 1, 28.6, "2019-12-07 12:35:33"),
    TemperatureBean("4312", 0, 31.4, "2019-12-07 12:25:03"),
    TemperatureBean("4302", 1, 30.1, "2019-12-07 12:32:17"),
    TemperatureBean("4305", 3, 31.5, "2019-12-07 12:33:11"),
    TemperatureBean("4310", 2, 29.9, "2019-12-07 12:34:42")
  )

  val updateData = Array[TemperatureBean](
    TemperatureBean("4310", 2, 30.4, "2019-12-07 12:35:42") // 设备ID为4310的传感器发生修改，温度值由29.9->30.4,时间由12:34:42->12:35:42
  )

  val deleteData = Array[TemperatureBean](
    TemperatureBean("4310", 2, 30.4, "2019-12-07 12:35:42") // 设备ID为4310的传感器要被删除，必须与最新的数据保持一致（如果字段值不同时无法删除）
  )

  def main(args: Array[String]): Unit = {
    //    System.setProperty(Configured.HADOOP_HOME_DIR, Configured.getHadoopHome)

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(APP_NAME)
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    spark.sparkContext.setLogLevel("OFF")
    import spark.implicits._

    // 类似Hive中的DB（basePath的schema决定了最终要操作的文件系统，如果是file:则为LocalFS，如果是hdfs:则为HDFS）
    val basePath = "~/tmp"
    // 类似Hive中的Table
    val tableName = "tbl_temperature_cow"
    // 数据所在的路径
    val path = s"$basePath/$tableName"
    val sourceDF = spark.sparkContext.parallelize(insertData).toDF()
    sourceDF.show()
    // 插入数据
    insert(sourceDF, tableName, "deviceId", "deviceType", "cdt", path)
    // 修改数据
    //    update(spark.createDataFrame(updateData.toBuffer.asJava, classOf[TemperatureBean]), tableName, "deviceId", "deviceType", "cdt", path)
    // 删除数据
    //    delete(spark.createDataFrame(deleteData.toBuffer.asJava, classOf[TemperatureBean]), tableName, "deviceId", "deviceType", "cdt", path)
    // 【查询方式1：默认为快照（基于行或列获取最新视图）查询
    //    query(spark.read.format(SOURCE).load(s"$path/*/*").orderBy($"deviceId".asc))
    //    query(spark.read.format(SOURCE).options(buildQuery(QUERY_TYPE_SNAPSHOT_OPT_VAL)).load(s"$path/*/*").withColumn("queryType", lit("查询方式为：快照（默认）")).orderBy($"deviceId".asc))
    // 【查询方式2：读时优化
    //    query(spark.read.format(SOURCE).options(buildQuery(QUERY_TYPE_READ_OPTIMIZED_OPT_VAL)).load(s"$path/*/*").withColumn("queryType", lit("查询方式为：读时优化")).orderBy($"deviceId".asc))
    // 【查询方式3：增量查询
    // 先取出最近一次提交的时间
    val commitTime = spark.read.format(SOURCE).load(s"$path/*/*").dropDuplicates("_hoodie_commit_time").select($"_hoodie_commit_time".as("commitTime")).orderBy($"commitTime".desc).first().getAs(0)
    // 再查询最近提交时间之后的数据
    //query(spark.read.format(SOURCE).options(buildQuery(QUERY_TYPE_INCREMENTAL_OPT_VAL, Option((commitTime.toLong - 2).toString))).load(s"$path/*/*").withColumn("queryType", lit("查询方式为：增量查询")).orderBy($"deviceId".asc).toDF())

    spark.close()
  }

  /**
   * 新增数据
   *
   * @param df              数据集
   * @param tableName       Hudi表
   * @param primaryKey      主键列名
   * @param partitionField  分区列名
   * @param changeDateField 变更时间列名
   * @param path            数据的存储路径
   */
  def insert(df: DataFrame, tableName: String, primaryKey: String, partitionField: String, changeDateField: String, path: String): Unit = {
    df.write.format(SOURCE)
      .options(Map(
        // 要操作的表
        TABLE_NAME -> tableName,
        // 操作的表类型（默认COW）
        TABLE_TYPE_OPT_KEY -> COW_TABLE_TYPE_OPT_VAL,
        // 执行insert操作
        OPERATION_OPT_KEY -> INSERT_OPERATION_OPT_VAL,
        // 设置主键列
        RECORDKEY_FIELD_OPT_KEY -> primaryKey,
        // 设置分区列,类似Hive的表分区概念
        PARTITIONPATH_FIELD_OPT_KEY -> partitionField,
        // 设置数据更新时间列,该字段数值大的数据会覆盖小的，必须是数值类型
        PRECOMBINE_FIELD_OPT_KEY -> changeDateField,
        // 要使用的索引类型，可用的选项是[SIMPLE|BLOOM|HBASE|INMEMORY]，默认为布隆过滤器
        INDEX_TYPE_PROP -> HoodieIndex.IndexType.BLOOM.name,
        // 执行insert操作的shuffle并行度
        INSERT_PARALLELISM -> "2"
      ))
      // 如果数据存在会覆盖
      .mode(Overwrite)
      .save(path)
  }

  /**
   * 修改数据
   *
   * @param df              数据集
   * @param tableName       Hudi表
   * @param primaryKey      主键列名
   * @param partitionField  分区列名
   * @param changeDateField 变更时间列名
   * @param path            数据的存储路径
   */
  def update(df: DataFrame, tableName: String, primaryKey: String, partitionField: String, changeDateField: String, path: String): Unit = {
    df.write.format(SOURCE)
      .options(Map(
        // 要操作的表
        TABLE_NAME -> tableName,
        // 操作的表类型（默认COW）
        TABLE_TYPE_OPT_KEY -> COW_TABLE_TYPE_OPT_VAL,
        // 执行upsert操作
        OPERATION_OPT_KEY -> UPSERT_OPERATION_OPT_VAL,
        // 设置主键列
        RECORDKEY_FIELD_OPT_KEY -> primaryKey,
        // 设置分区列,类似Hive的表分区概念
        PARTITIONPATH_FIELD_OPT_KEY -> partitionField,
        // 设置数据更新时间列,该字段数值大的数据会覆盖小的
        PRECOMBINE_FIELD_OPT_KEY -> changeDateField,
        // 要使用的索引类型，可用的选项是[SIMPLE|BLOOM|HBASE|INMEMORY]，默认为布隆过滤器
        INDEX_TYPE_PROP -> HoodieIndex.IndexType.BLOOM.name,
        // 执行upsert操作的shuffle并行度
        UPSERT_PARALLELISM -> "2"
      ))
      // 如果数据存在会覆盖
      .mode(Append)
      .save(path)
  }

  /**
   * 删除数据
   *
   * @param df              数据集
   * @param tableName       Hudi表
   * @param primaryKey      主键列名
   * @param partitionField  分区列名
   * @param changeDateField 变更时间列名
   * @param path            数据的存储路径
   */
  def delete(df: DataFrame, tableName: String, primaryKey: String, partitionField: String, changeDateField: String, path: String): Unit = {
    df.write.format(SOURCE)
      .options(Map(
        // 要操作的表
        TABLE_NAME -> tableName,
        // 操作的表类型（默认COW）
        TABLE_TYPE_OPT_KEY -> COW_TABLE_TYPE_OPT_VAL,
        // 执行delete操作
        OPERATION_OPT_KEY -> DELETE_OPERATION_OPT_VAL,
        // 设置主键列
        RECORDKEY_FIELD_OPT_KEY -> primaryKey,
        // 设置分区列,类似Hive的表分区概念
        PARTITIONPATH_FIELD_OPT_KEY -> partitionField,
        // 设置数据更新时间列,该字段数值大的数据会覆盖小的
        PRECOMBINE_FIELD_OPT_KEY -> changeDateField,
        // 要使用的索引类型，可用的选项是[SIMPLE|BLOOM|HBASE|INMEMORY]，默认为布隆过滤器
        INDEX_TYPE_PROP -> HoodieIndex.IndexType.BLOOM.name,
        // 执行delete操作的shuffle并行度
        DELETE_PARALLELISM -> "2",
        // 删除策略有软删除（保留主键且其余字段为null）和硬删除（从数据集中彻底删除）两种，此处为硬删除
        PAYLOAD_CLASS_OPT_KEY -> classOf[EmptyHoodieRecordPayload].getName
      ))
      // 如果数据存在会覆盖
      .mode(Append)
      .save(path)
  }

  /**
   * 查询类型
   * <br>Hoodie具有3种查询模式：</br>
   * <br>1、默认是快照模式（Snapshot mode,根据行和列数据获取最新视图）</br>
   * <br>2、增量模式（incremental mode,查询从某个commit时间片之后的数据）</br>
   * <br>3、读时优化模式（Read Optimized mode,根据列数据获取最新视图）</br>
   *
   * @param queryType
   * @param queryTime
   * @return
   */
  def buildQuery(queryType: String, queryTime: Option[String] = Option.empty) = Map(
    queryType match {
      // 如果是读时优化模式（read_optimized,根据列数据获取最新视图）
      case QUERY_TYPE_READ_OPTIMIZED_OPT_VAL => QUERY_TYPE_OPT_KEY -> QUERY_TYPE_READ_OPTIMIZED_OPT_VAL
      // 如果是增量模式（incremental mode,查询从某个时间片之后的新数据）
      case QUERY_TYPE_INCREMENTAL_OPT_VAL => QUERY_TYPE_OPT_KEY -> QUERY_TYPE_INCREMENTAL_OPT_VAL
      // 默认使用快照模式查询（snapshot mode,根据行和列数据获取最新视图）
      case _ => QUERY_TYPE_OPT_KEY -> QUERY_TYPE_SNAPSHOT_OPT_VAL
    },
    if (queryTime.nonEmpty) BEGIN_INSTANTTIME_OPT_KEY -> queryTime.get else BEGIN_INSTANTTIME_OPT_KEY -> "0"
  )


}
