package com.lhm.atguigu.SparkCore

import org.apache.spark.{SparkConf, SparkContext}

object GroupByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("GroupByKey")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array("Count","lihuangmin","AAA","BBBB","CCC"))
    sourceRDD.map((_,1)).groupByKey().collect.foreach(println)
  }
}
