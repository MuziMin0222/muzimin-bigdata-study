package com.muzimin.exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/*
Question3:
要求得到结果，所有用户历史全量的购买商品和数量，
结果写入hdfs目录/result3中
结果结构 由 {姓名} ，{商品信息} ，结果如下：
酱酱,{items:[{item:杯子,num:5},{item:绳子,num6}]}
 */
object Question3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Question3").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val memberRDD: RDD[String] = sc.textFile("data/member/*")
    val orderRDD = sc.textFile("data/order/*")

    val newMemberRDD = memberRDD.map {
      line => {
        val word_arr = line.split(",")
        (word_arr(1), word_arr(0))
      }
    }

    val newOrderRDD = orderRDD.map(line => {
      val word_arr = line.split(",")
      (word_arr(1), (word_arr(2), 1)) //(10001,(绳子,1))
    })

    val JoinRDD = newOrderRDD.join(newMemberRDD) //(10001,((杯子,1),酱酱))
      .map(t => {
        (t._2._2 + ":" + t._2._1._1, t._2._1._2)
      })
      .reduceByKey(_ + _)
      .map(t => {
        (t._1.split(":")(0), "item:" + t._1.split(":")(1) + ",num:" + t._2)
      })
      .reduceByKey((x, y) => {
        x + "," + y
      }).map(t => {
      t._1 + "," + t._2
    })

    JoinRDD.saveAsTextFile("result3")
    JoinRDD.saveAsTextFile("hdfs://bd1:9000/hypers/result3")


    JoinRDD.collect().foreach(x => {
      print(x + "\t")
    })
    println()

  }
}
