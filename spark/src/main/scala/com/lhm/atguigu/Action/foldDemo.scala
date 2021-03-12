package com.lhm.atguigu.Action

import org.apache.spark.{SparkConf, SparkContext}

object foldDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array(1,2,3,4,5),2)
    println(sourceRDD.fold(0)(_ + _))//15
    println(sourceRDD.fold(10)(_ + _))//45
  }
}
