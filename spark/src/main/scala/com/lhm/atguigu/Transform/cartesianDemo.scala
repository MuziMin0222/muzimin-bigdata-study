package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object cartesianDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(1 to 3)
    val sourceRDD1 = sc.makeRDD(3 to 6)

    val res = sourceRDD.cartesian(sourceRDD1)
    res.foreach(println)
  }
}
