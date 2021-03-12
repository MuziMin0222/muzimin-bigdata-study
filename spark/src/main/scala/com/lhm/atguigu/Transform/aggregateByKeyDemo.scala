package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object aggregateByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
    sourceRDD.glom().collect().foreach{
      t => t.foreach(println)
      println("====================")
    }

    val resRDD = sourceRDD.aggregateByKey(0)(math.max(_,_),_+_)
    resRDD.collect().foreach(println)
  }
}
