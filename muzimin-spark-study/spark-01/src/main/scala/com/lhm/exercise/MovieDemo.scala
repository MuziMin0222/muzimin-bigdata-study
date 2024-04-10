package com.lhm.exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//男女用户的比例
object MovieDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("movies").set("spark.master", "local[*]")
    val sc = SparkContext.getOrCreate(conf)

    //男女用户的比例
    //读取用户表
    val userRDD = sc.textFile("D:\\code\\workspace_IdeaUi\\Spark\\src\\data\\users.dat")
    //数据清洗：用户id：：性别：：：年龄：：职业代码：：邮编
    val genderRDD = userRDD.map(line => (line.split("::")(1), 1))
    //统计男女和
    val genderCount = genderRDD.reduceByKey(_ + _)
    genderCount.foreach(println)

    //每个用户的平均评分中，排名前十和最后十名的用户及其评分分别是多少
    //评分表：  用户id：：电影id：：评分：：时间戳
    val ratingRDD = sc.textFile("D:\\code\\workspace_IdeaUi\\Spark\\src\\data\\ratings.dat")
    val userAndRating: RDD[(String, Double)] = ratingRDD.map(line => {
      val Array(user_id, _, rate, _) = line.split("::")
      (user_id, rate.toDouble)
    })
    //每个用户进行分组
    val userAndArrayRDD = userAndRating.groupByKey()
    //每个用户的平均评分
    val avgRateRdd = userAndArrayRDD.mapValues(itea => {
      val list = itea.toList
      val size = list.size;
      val sum = list.sum;
      sum / size;
    })
    //计算平均值的另外一种方法
    val resultRDD: RDD[(String, (Double, Int))] = userAndRating.combineByKey(
      rate => (rate,1),
      (c:(Double,Int),v:Double) => (c._1 + v , c._2 + 1),
      (c1:(Double,Int),c2:(Double,Int)) => (c1._1 + c2._1, c1._2 + c2._2)
    )
    resultRDD.mapValues(x => x._1/x._2).foreach(println);


    //按照评分进行降序排序,取出前十的方法
    avgRateRdd.sortBy(_._2,false).take(10).foreach(println)
    //按照评分进行升序排序,取出前十的方法
    avgRateRdd.sortBy(_._2).take(10).foreach(println)
  }
}
