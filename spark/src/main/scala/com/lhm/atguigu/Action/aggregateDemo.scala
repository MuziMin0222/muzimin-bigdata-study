package com.lhm.atguigu.Action

import org.apache.spark.{SparkConf, SparkContext}

object aggregateDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array(1,2,6,3,7),2)
    println(sourceRDD.aggregate(0)(_ + _, _ + _)) //19
    //两个分区，分区间操作都加上10，分区内操作加上10
    println(sourceRDD.aggregate(10)(_ + _, _ + _)) //49
  }
}
