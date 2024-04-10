package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object DistinctDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array(1,1,1,3,4,5,3,2,5,6,32,1,32))
    val DistinctRDD = sourceRDD.distinct()
    DistinctRDD.foreach(println)
  }
}
