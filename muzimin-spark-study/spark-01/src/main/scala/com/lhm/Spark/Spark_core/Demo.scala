package com.lhm.Spark.Spark_core

import org.apache.spark.{SparkConf, SparkContext}

object Demo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Demo")
    val sc = new SparkContext(conf)

    var count = 0;
    sc.makeRDD(Array(1,2,3,4,5)).foreach(x => count + x)   //发送到Executor中进行执行，但是结果没有发送过来

    println(count)
  }
}
