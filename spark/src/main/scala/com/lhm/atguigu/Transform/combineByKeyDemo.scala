package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object combineByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2)
    sourceRDD.glom().collect().foreach{
      x => x.foreach(println)
      println("============")
    }

    //将相同key对应的值相加，同时记录该key出现的次数，放入一个二元组
    val CombineByKeyRDD = sourceRDD.combineByKey(
      (_, 1),
      (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
      (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    )
    CombineByKeyRDD.collect().foreach(println)
    println("==============================")

    //计算平均值
    val res = CombineByKeyRDD.map {
      case (key, value) => (key, value._1 / value._2.toDouble)
    }
    res.collect().foreach(println)
  }
}
