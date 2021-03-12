package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object RepartitionDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List(1,2,3,4,5,6),4)
    println(sourceRDD.partitions.size)
    val RepartitionRDD = sourceRDD.repartition(2)
    println(RepartitionRDD.partitions.size)
  }
}
