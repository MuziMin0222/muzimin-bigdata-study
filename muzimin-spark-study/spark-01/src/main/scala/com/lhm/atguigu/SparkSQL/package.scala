package com.lhm.atguigu

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

package object SparkSQL {
  val conf = new SparkConf().setAppName("SparkSqlDemo").setMaster("local[*]")

  //创建sparkSQL的环境对象
  val spark = SparkSession.builder().config(conf).getOrCreate()
}
