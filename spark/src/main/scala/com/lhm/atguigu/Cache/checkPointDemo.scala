package com.lhm.atguigu.Cache

import org.apache.spark.{SparkConf, SparkContext}

object checkPointDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //设置检查点
    sc.setCheckpointDir("CheckPointDir")

    val rdd = sc.parallelize(Array("atguigu"))
    //将RDD转换为携带当前时间戳并做checkpoint
    val TimeRDD = rdd.map(_ + System.currentTimeMillis())
    TimeRDD.checkpoint()
    for (_ <- 1 to 10){
      TimeRDD.collect().foreach(println)//十次结果一致
    }
  }
}
