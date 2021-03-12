package com.lhm.Demo

import org.apache.spark.{SparkConf, SparkContext}

object NewRDDDemo {
  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4,5)

    val conf = new SparkConf().setMaster("local[*]").setAppName("RDDDemo")
    val sc = new SparkContext(conf)

    //创建rdd方法一
    val rdd = sc.parallelize(arr)
    rdd.foreach(println)//从输出结果看，是多线程的

    println("---------------")

    //创建rdd方法二
    val rdd1 = sc.makeRDD(arr)
    rdd1.foreach(println)
  }
}
