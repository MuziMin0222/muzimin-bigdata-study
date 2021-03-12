package com.lhm.atguigu.Action

import org.apache.spark.{SparkConf, SparkContext}

object CountDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(1 to 10, 2)
    println(sourceRDD.count())//10
  }
}
