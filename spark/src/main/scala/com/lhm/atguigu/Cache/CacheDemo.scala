package com.lhm.atguigu.Cache

import org.apache.spark.{SparkConf, SparkContext}

object CacheDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array("atguigu"))

    //将RDD转换为携带当前时间戳不做缓存
    val RDDNotCache = sourceRDD.map(_.toString + System.currentTimeMillis())
    for (_ <- 1 to 10){
      RDDNotCache.collect().foreach(println)//十个结果，每个都不一样
    }
    println("========================================")
    //将RDD转换为携带当前时间戳并做缓存
    val CacheRDD = sourceRDD.map(_ + System.currentTimeMillis()).cache()
    for (_ <- 1 to 10){
      CacheRDD.collect().foreach(println)//十个结果，每一个都一样
    }
  }
}
