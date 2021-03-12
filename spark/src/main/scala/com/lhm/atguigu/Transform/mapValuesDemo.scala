package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object mapValuesDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array((1,"a"),(1,"d"),(2,"b"),(3,"c")))

    val resRDD = sourceRDD.mapValues(s => s + "_lihuangmin")
    resRDD.foreach(println)
  }
}
