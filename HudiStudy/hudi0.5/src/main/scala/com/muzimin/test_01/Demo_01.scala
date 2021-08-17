package com.muzimin.test_01

import com.muzimin.bean.DemoEntity
import com.muzimin.common.util.JsonUtil
import org.apache.hudi.{DataSourceReadOptions, DataSourceWriteOptions}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-04-28 14:30
 *       ${description}
 **/
object Demo_01 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("dwd_member_import")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .setMaster("local[*]")
    val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    insertData(sparkSession)
  }

  //插入数据
  def insertData(sparkSession: SparkSession) = {
    import org.apache.spark.sql.functions._
    import sparkSession.implicits._
    val commitTime = System.currentTimeMillis().toString //生成提交时间
    val sourceDF = sparkSession.read.text("file:///D:\\video\\data\\test1.json")

    sourceDF.show()

    val df = sourceDF.mapPartitions(partitions => {
      partitions.map(item => {
        val jsonObject = JsonUtil.getJsonData(item.getString(0))
        DemoEntity(jsonObject.getIntValue("uid"), jsonObject.getString("uname"), jsonObject.getString("dt"))
      })
    })

    df.show()

    val result = df.withColumn("ts", lit(commitTime)) //添加ts 时间戳列
      .withColumn("uuid", col("uid"))
      .withColumn("hudipart", col("dt")) //增加hudi分区列
    result.write.format("org.apache.hudi")
      .option("hoodie.insert.shuffle.parallelism", 2)
      .option("hoodie.upsert.shuffle.parallelism", 2)
      .option("PRECOMBINE_FIELD_OPT_KEY", "ts") //指定提交时间列
      .option("RECORDKEY_FIELD_OPT_KEY", "uuid") //指定uuid唯一标示列
      .option("hoodie.table.name", "youfanTable")
      .option("hoodie.datasource.write.partitionpath.field", "hudipart") //分区列
      .mode(SaveMode.Overwrite)
      .save("file:///D:\\video\\data\\hudi")
  }

  //更新数据
  def updateData(sparkSession: SparkSession) = {
    import org.apache.spark.sql.functions._
    import sparkSession.implicits._
    val commitTime = System.currentTimeMillis().toString //生成提交时间
    val df = sparkSession.read.text("/test/test22")
      .mapPartitions(partitions => {
        partitions.map(item => {
          val jsonObject = JsonUtil.getJsonData(item.getString(0))
          DemoEntity(jsonObject.getIntValue("uid"), jsonObject.getString("uname"), jsonObject.getString("dt"))
        })
      })
    val result = df.withColumn("ts", lit(commitTime)) //添加ts 时间戳列
      .withColumn("uuid", col("uid")) //添加uuid 列
      .withColumn("hudipart", col("dt")) //增加hudi分区列
    result.write.format("org.apache.hudi")
      //.option(DataSourceWriteOptions.TABLE_TYPE_OPT_KEY, DataSourceWriteOptions.MOR_TABLE_TYPE_OPT_VAL)   //指定表类型为merge_on_read
      .option(DataSourceWriteOptions.TABLE_TYPE_OPT_KEY, DataSourceWriteOptions.COW_TABLE_TYPE_OPT_VAL) //指定表类型为COPY_ON_WRITE
      .option("hoodie.insert.shuffle.parallelism", 2)
      .option("hoodie.upsert.shuffle.parallelism", 2)
      .option("PRECOMBINE_FIELD_OPT_KEY", "ts") //指定提交时间列
      .option("RECORDKEY_FIELD_OPT_KEY", "uuid") //指定uuid唯一标示列
      .option("hoodie.table.name", "youfanTable")
      .option("hoodie.datasource.write.partitionpath.field", "hudipart") //分区列
      .mode(SaveMode.Append)
      .save("/youfan/data/hudi")
  }

  //查询全量数据
  def qryData(sparkSession: SparkSession) = {
    val df = sparkSession.read.format("org.apache.hudi")
      .load("/youfan/data/hudi/*/*")
    df.show()
    println("count:" + df.count())
  }

  //增量查询数据
  def incrementalQuery(sparkSession: SparkSession) = {
    val beginTime = 20210104153941l
    val df = sparkSession.read.format("org.apache.hudi")
      .option(DataSourceReadOptions.QUERY_TYPE_OPT_KEY, DataSourceReadOptions.QUERY_TYPE_INCREMENTAL_OPT_VAL) //指定模式为增量查询
      .option(DataSourceReadOptions.BEGIN_INSTANTTIME_OPT_KEY, beginTime) //设置开始查询的时间戳  不需要设置结束时间戳
      .load("/youfan/data/hudi")
    df.show()
    println(df.count())
  }

  //时间区间查询
  def InTimeQuery(sparkSession: SparkSession) = {
    val beginTime = 20210102153941l
    val endTime = 20210104153941l
    val df = sparkSession.read.format("org.apache.hudi")
      .option(DataSourceReadOptions.QUERY_TYPE_OPT_KEY, DataSourceReadOptions.QUERY_TYPE_INCREMENTAL_OPT_VAL) //指定模式为增量查询
      .option(DataSourceReadOptions.BEGIN_INSTANTTIME_OPT_KEY, beginTime) //设置开始查询的时间戳
      .option(DataSourceReadOptions.END_INSTANTTIME_OPT_KEY, endTime)
      .load("/youfan/data/hudi")
    df.show()
    println(df.count())
  }
}
