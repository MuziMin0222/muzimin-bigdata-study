package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object foldByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8)),3)
    sourceRDD.glom().collect().foreach{
      t => t.foreach(println)
        println("====================")
    }

    val resRDD = sourceRDD.foldByKey(0)(_+_)
    resRDD.collect().foreach(println)
  }
}
