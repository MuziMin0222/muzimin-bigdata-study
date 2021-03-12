package com.lhm.Spark

import org.apache.spark.{SparkConf, SparkContext}

object WC {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("1").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val lines = sc.textFile("src/1")

    val words = lines.flatMap(_.split(" "))
    val pairs = words.map((_, 1))
    val reduce = pairs.reduceByKey(_ + _)
    reduce.saveAsTextFile("out")
    sc.stop()
  }
}