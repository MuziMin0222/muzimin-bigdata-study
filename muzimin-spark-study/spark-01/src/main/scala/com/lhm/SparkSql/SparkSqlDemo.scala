package com.lhm.SparkSql

import org.apache.spark.sql.SparkSession

object SparkSqlDemo {
  def main(args: Array[String]): Unit = {
    //1、构建sparkSession
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    //导入隐式转化包
    import spark.implicits._

    //读取文件
    val df = spark.read.text("D:\\code\\workspace_IdeaUi\\Spark\\src\\main\\resource\\log4j.properties")
    df.show()

    println(spark)

    spark.close()
  }
}
