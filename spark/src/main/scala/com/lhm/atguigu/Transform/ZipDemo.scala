package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object ZipDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(1 to 3,3)
    val sourceRDD1 = sc.makeRDD(4 to 6,3)

    val resRDD = sourceRDD.zip(sourceRDD1)

    resRDD.foreach(println)
  }
}
