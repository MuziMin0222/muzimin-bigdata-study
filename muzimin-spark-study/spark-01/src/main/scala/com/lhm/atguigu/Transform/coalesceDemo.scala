package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object coalesceDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array(1,2,3,4,5,6),4)
    val coalesceRDD = sourceRDD.coalesce(3)
    coalesceRDD.foreach(println)
  }
}
