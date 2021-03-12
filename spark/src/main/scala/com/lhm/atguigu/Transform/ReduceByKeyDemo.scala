package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object ReduceByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List("hello","world","hello","spark"))
    val pariRDD = sourceRDD.map((_,1))
    val reduceRDD = pariRDD.reduceByKey((x,y) => x+y)
    reduceRDD.collect().foreach(println)
  }
}
