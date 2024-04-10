package com.lhm.atguigu.Action

import org.apache.spark.{SparkConf, SparkContext}

object takeOrderedDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array(1,2,6,3,7))
    sourceRDD.takeOrdered(3).foreach(println)//1,2,3
  }
}
