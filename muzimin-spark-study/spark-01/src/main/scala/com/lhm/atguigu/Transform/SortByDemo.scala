package com.lhm.atguigu.Transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SortByDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD: RDD[Int] = sc.makeRDD(Array(3,4,7,5,2,7,3,6))
    //按照数组中数字的大小进行排序，默认是升序
    val sortByRDD: RDD[Int] = sourceRDD.sortBy(x => x)
    sortByRDD.collect().foreach(println)

    //按照数组中数字与3的余数大小进行排序
    val sortByRDD1 = sourceRDD.sortBy(s => s%3)
    sortByRDD1.collect().foreach(println)
  }
}
