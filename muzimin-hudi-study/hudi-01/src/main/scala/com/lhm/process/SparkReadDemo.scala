package com.lhm.process

import org.apache.spark.sql.SparkSession

/**
 * @author : 李煌民
 * @date : 2021-02-18 17:41
 *       ${description}
 **/
object SparkReadDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.default.parallelism", 4)
      .appName("Spark Hudi Query")
      .getOrCreate()

    // 构建元数据
    val tableName = "hudi_weblog"
    val hdfsPath = "/datalake/test/hudi_first"

    // 基于Hudi创建DF
    val weblogDF = spark
      .read
      .format("hudi")
      .load(hdfsPath + "/2021")

    weblogDF.createOrReplaceTempView(tableName)

    spark.sql(s"""
                 | select
                 |   *
                 | from
                 |   ${tableName}
       """.stripMargin)
      .show(20)
  }
}
