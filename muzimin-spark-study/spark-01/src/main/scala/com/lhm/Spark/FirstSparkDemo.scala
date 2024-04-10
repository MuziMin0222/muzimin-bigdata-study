package com.lhm.Spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FirstSparkDemo {
  def main(args: Array[String]): Unit = {
    //1、构建spark context 对象
    val conf = new SparkConf()

    //spark的运行模式，必须添加
    conf.setMaster("local")
    //spark工作的名称，必须添加
    conf.setAppName("first")

    // val sc = new SparkContext(conf);
    val sc = SparkContext.getOrCreate(conf)
    //textFile是一个获取RDD操作
    val line: RDD[String] = sc.textFile("file:///D:/code/IODemo/a.txt")
    println(line.toString())
    println(line.count())

    //结束上下文对象
    sc.stop()
  }
}
