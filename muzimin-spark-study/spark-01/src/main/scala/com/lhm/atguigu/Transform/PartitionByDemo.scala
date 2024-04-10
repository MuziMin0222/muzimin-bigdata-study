package com.lhm.atguigu.Transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object PartitionByDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array((1,"a"),(2,"b"),(3,"c"),(4,"d")),4)

    println(sourceRDD.partitions.size)

    val resRDD = sourceRDD.partitionBy(new HashPartitioner(2))
    println(resRDD.partitions.size)
  }
}
