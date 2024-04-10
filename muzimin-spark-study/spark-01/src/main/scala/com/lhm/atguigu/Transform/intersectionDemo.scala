package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object intersectionDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD1 = sc.makeRDD(1 to 5)
    val sourceRDD2 = sc.makeRDD(3 to 8)

    val resRDD = sourceRDD1.intersection(sourceRDD2)
    resRDD.foreach(println)
  }
}
