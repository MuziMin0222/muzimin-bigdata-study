package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object RDDOper {
  def main(args: Array[String]): Unit = {
    //使用map算子将集合中的元素每个都乘2返回结果
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operation")
    val sc = new SparkContext(sparkConf)

    val listRDD = sc.makeRDD(1 to 10)

    val mapParWithIndexRDD = listRDD.mapPartitionsWithIndex {
      case (num, datas) => {
        datas.map((_, "分区号：" + num))
      }
    }
    mapParWithIndexRDD.foreach(println)

    val MapParRDD = listRDD.mapPartitions(data => {
      data.map(_ * 2)
    })
    MapParRDD.foreach(println)

    val res = listRDD.map( _ * 2)

    res.collect().foreach(println)


  }
}
