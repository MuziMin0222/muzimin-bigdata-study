package com.muzimin.exercise

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.{SparkConf, SparkContext}

//要求得到结果，每个人每日的消费总金额
//结果写入hdfs目录 /result1中
//结果结构 由{姓名}，{总消费金额} ，{日期} 组成 ，结果如下：
//酱酱，9999.9 ,20191010
object Question1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Question1")
    val sc = new SparkContext(conf)

    val memberRDD = sc.textFile("hdfs://bd1:9000/hypers/member/*")
    val orderRDD = sc.textFile("hdfs://bd1:9000/hypers/order/*")

    val newMemberRDD =
      memberRDD.map(line => {
        val wordArr: Array[String] = line.split(",")
        (wordArr(1), wordArr(0))
      })

    val newOrderRDD = orderRDD.map(line => {
      val wordArr: Array[String] = line.split(",")
      (wordArr(1) , (wordArr(3) , wordArr(4)))
    })

    val joinRDD = newMemberRDD.join(newOrderRDD).map(t2 => {
      val tuple = (t2._2._1, t2._2._2)
      ((tuple._1, new SimpleDateFormat("yyyyMMdd").format(new Date(tuple._2._2.toLong * 1000))), tuple._2._1.toInt)
    }).reduceByKey(_ + _).map(t => {
      t._1._1 + "," + t._1._2 + "," + t._2
    })

//    joinRDD.saveAsTextFile("hdfs://bd1:9000/hypers/result1")
//
//    joinRDD.saveAsTextFile("result1")

    joinRDD.collect().foreach(x => {
      print(x + "\t")
      println()
    })
    println()
  }
}
