package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperDemo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(1 to 10)
    val groupRDD = sourceRDD.groupBy(_ % 2)
    groupRDD.foreach(println)
  }
}
