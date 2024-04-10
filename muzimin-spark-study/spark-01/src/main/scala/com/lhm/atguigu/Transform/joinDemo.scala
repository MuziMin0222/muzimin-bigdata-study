package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object joinDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(Array((1,"a"),(2,"b"),(3,"c")))
    val rdd2 = sc.makeRDD(Array((1,4),(2,5),(3,6)))

    val resRDD = rdd1.join(rdd2)
    resRDD.collect().foreach(println)
  }
}
