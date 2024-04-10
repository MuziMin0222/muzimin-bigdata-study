package com.lhm.Spark.Spark_core

import org.apache.spark.{SparkConf, SparkContext}

//自定义函数并使用
object SelfFunction {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Function")
    val sc = new SparkContext(conf)

    sc.textFile("hdfs://bd1:9000/data/weather/999999-99999-1990").map(line => MyFunction.fun(line)).foreach(println)
  }
}

object MyFunction{
  def fun(s:String) = {
    s + "lihuangmin"
  }
}
