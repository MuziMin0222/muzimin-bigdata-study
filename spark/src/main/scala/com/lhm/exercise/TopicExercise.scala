package com.lhm.exercise

import org.apache.spark.{SparkConf, SparkContext}

object TopicExercise {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().set("spark.master", "local[*]").setAppName("TopicExer")

    val sc = SparkContext.getOrCreate(conf)

    //读取用户的订阅数据 userID topicID
    val userTopicRDD = sc.textFile("")
    //读取用户的浏览数据
    val userLinkRDD = sc.textFile("")
    //整理数据
    val userIdAndTopicIDRDD = userTopicRDD.map(line => {
      val Array(userId, topicId) = line.split(",")
      ((userId, topicId),topicId)
    })
    val userIdAndLinkIDRDD = userLinkRDD.map(line => {
      val Array(userId,linkTopicId) = line.split(",")
      ((userId,linkTopicId),linkTopicId)
    })
    //核心计算，推荐用户未订阅的主题
//    userIdAndLinkIDRDD.subtractByKey()
  }
}
