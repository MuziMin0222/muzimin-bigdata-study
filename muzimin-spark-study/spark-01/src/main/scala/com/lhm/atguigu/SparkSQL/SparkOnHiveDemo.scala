package com.lhm.atguigu.SparkSQL

object SparkOnHiveDemo {
  def main(args: Array[String]): Unit = {
    spark.sql("show tables").show()
  }
}
