package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object SampleDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(1 to 10)

    val sampleRDD = sourceRDD.sample(false,0.6,1)
    sampleRDD.foreach(println)
  }
}
