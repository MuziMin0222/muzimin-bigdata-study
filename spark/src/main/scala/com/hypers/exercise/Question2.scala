package com.hypers.exercise

import org.apache.spark.{SparkConf, SparkContext}

/*
Question2:
要求得到结果，当天每人的消费金额排名，（相同消费金额排名相同），降序排列
结果写入hdfs目录/result2中
结果结构 由 {姓名} ，{总消费金额} ，{排名}， {日期}
酱酱，9999,1,20191010
饺饺,  99,2,20191010
小白菜，99,2,20191010
 */
object Question2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Question2")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.textFile("result1/part-00000")

    val sortByRDD =
      sourceRDD.map(line => {
        val word_arr = line.split(",")
        (word_arr(2), word_arr(0) + "," + word_arr(1))
      })
        .sortBy(t => {
          t._1.toInt
        }, false)
        .zipWithIndex().map { case (x, y) => (x, y + 1) }          //问题？？？？
        .map {
          t => {
            //姓名} ，{总消费金额} ，{排名}， {日期}
            t._1._2.split(",")(0) + "," + t._1._1 + "," + t._2 + "," + t._1._2.split(",")(1)
          }
        }

//    sourceRDD.saveAsTextFile("hdfs://bd1:9000/hypers/result2")
//    sortByRDD.saveAsTextFile("result2")

    sortByRDD.collect().foreach(x => {
      print(x + "\t")
      println()
    })
    println()


    Thread.sleep(900000)
  }
}
