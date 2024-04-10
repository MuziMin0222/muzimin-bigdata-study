package com.lhm.atguigu.Action

import org.apache.spark.{SparkConf, SparkContext}

object ReduceDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(1 to 10, 2)
    println(sourceRDD.reduce(_ + _)) //55

    val sourceRDD1 = sc.makeRDD(Array(("a", 1), ("a", 3), ("c", 3), ("d", 5)))
    println(sourceRDD1.reduce((x, y) => (x._1 + y._1, x._2 + y._2)))//(cdaa,12)
  }
}
