package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object sortByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array((3,"aa"),(6,"cc"),(2,"bb"),(1,"dd")))
    //升序排序
    sourceRDD.sortByKey(true).collect().foreach(println)
    //降序排序
    sourceRDD.sortByKey(false).collect().foreach(println)
  }
}
