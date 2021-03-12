package com.lhm.copyOnWrite

import org.apache.hudi.DataSourceWriteOptions
import org.apache.hudi.config.{HoodieIndexConfig, HoodieWriteConfig}
import org.apache.hudi.index.HoodieIndex
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-02-19 10:58
 *       数据存在时修改，不存在时新增
 **/
object UpsertDemo {
  def main(args: Array[String]): Unit = {

  }

  def upsertWithOutPartition() = {
    val spark = SparkSession
      .builder
      .appName("hudi upsert")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .master("local[3]")
      .getOrCreate()

    val insertData = spark
      .read
      .parquet("/tmp/1563959377699.parquet")


    insertData.write.format("org.apache.hudi")
      // 设置主键列名
      .option(DataSourceWriteOptions.RECORDKEY_FIELD_OPT_KEY, "rowkey")
      // 设置数据更新时间的列名
      .option(DataSourceWriteOptions.PRECOMBINE_FIELD_OPT_KEY, "lastupdatedttm")
      // 表名称设置
      .option(HoodieWriteConfig.TABLE_NAME, "test")
      // 并行度参数设置
      .option("hoodie.insert.shuffle.parallelism", "2")
      .option("hoodie.upsert.shuffle.parallelism", "2")
      .mode(SaveMode.Append)
      // 写入路径设置
      .save("/tmp/hudi")
  }

  def upsertWithPartition() = {
    val spark = SparkSession
      .builder
      .appName("upsert partition")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .master("local[*]")
      .getOrCreate()

    val upsertData = spark
      .read
      .parquet("/tmp/1563959377699.parquet")

    upsertData
      .write
      .format("org.apache.hudi")
      .option(DataSourceWriteOptions.RECORDKEY_FIELD_OPT_KEY, "rowkey")
      .option(DataSourceWriteOptions.PRECOMBINE_FIELD_OPT_KEY, "lastupdatedttm")
      // 分区列设置
      .option(DataSourceWriteOptions.PARTITIONPATH_FIELD_OPT_KEY, "dt")
      .option(HoodieWriteConfig.TABLE_NAME, "test_partition")
      .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
      .option("hoodie.insert.shuffle.parallelism", "2")
      .option("hoodie.upsert.shuffle.parallelism", "2")
      .mode(SaveMode.Append)
      .save("/tmp/hudi")
  }
}
