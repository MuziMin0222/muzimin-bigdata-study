package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object GroupByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List("hello","world","hello","scala","hello","spark"))
    val PairRDD = sourceRDD.map((_,1))
    val groupByKeyRDD = PairRDD.groupByKey()
    groupByKeyRDD.collect().foreach(println)
    println("=====================")

    //就相当于一个词频统计
    groupByKeyRDD.map(t => (t._1,t._2.sum)).collect().foreach(println)
  }
}
