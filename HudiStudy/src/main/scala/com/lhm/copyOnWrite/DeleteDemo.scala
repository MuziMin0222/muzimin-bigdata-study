package com.lhm.copyOnWrite

import org.apache.hudi.DataSourceWriteOptions
import org.apache.hudi.config.HoodieWriteConfig
import org.apache.spark.sql.SparkSession

/**
 * @author : 李煌民
 * @date : 2021-02-19 11:07
 *       ${description}
 **/
object DeleteDemo {
  def main(args: Array[String]): Unit = {

  }

  def delete() = {
    val spark = SparkSession
      .builder
      .appName("delta insert")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .master("local[3]")
      .getOrCreate()

    val deleteData = spark.read.parquet("/tmp/1563959377698.parquet")

    deleteData
      .write
      .format("com.uber.hoodie")
      // 设置主键列名
      .option(DataSourceWriteOptions.RECORDKEY_FIELD_OPT_KEY, "rowkey")
      // 设置数据更新时间的列名
      .option(DataSourceWriteOptions.PRECOMBINE_FIELD_OPT_KEY, "lastupdatedttm")
      // 表名称设置
      .option(HoodieWriteConfig.TABLE_NAME, "test")
      // 硬删除配置
      .option(DataSourceWriteOptions.PAYLOAD_CLASS_OPT_KEY, "org.apache.hudi.EmptyHoodieRecordPayload")
  }
}
