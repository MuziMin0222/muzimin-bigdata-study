package com.muzimin.exercise

import java.util

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Question3").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val v_idRDD: RDD[(String, String)] = sc.textFile("data/Test/a.csv").map(line =>{
      val strs = line.split(",")
      (strs(1),strs(0))   //(u_id,v_id)
    }).distinct()


    val u_idRDD: RDD[(String, (String, String))] = sc.textFile("data/Test/b.csv").map(line =>{
      val strs: Array[String] = line.split(",")
      (strs(0),(strs(1),strs(2)))   //(u_id,(机型,city))
    })

    val JoinRDD = v_idRDD.join(u_idRDD).map(tuple => {
      val city = tuple._2._2._2
      val xinhao = tuple._2._2._1
      val v_id = tuple._2._1
//      val u_id = tuple._1
      ((city,xinhao,v_id),1)
    }).reduceByKey(_ + _)

    JoinRDD.collect().foreach(line =>{
      print(line + "\t")
    })
  }
}
