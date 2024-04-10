package com.lhm.atguigu.Super

import org.apache.spark.{SparkConf, SparkContext}

object broadcastDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //声明广播变量
    val rdd = sc.broadcast(Array(1,2,3))
    //得到广播变量的值
    rdd.value.foreach(println)
  }
}
