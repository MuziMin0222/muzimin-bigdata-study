package com.lhm.Spark.Spark_core

import org.apache.spark.sql.SparkSession
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object TransformDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Demo")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    val sourceRDD = sc.makeRDD(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
    val newRDD = sc.makeRDD(Array(5,6,7))
    val sourceDF = sourceRDD.toDF("id")
    val sourceDS = sourceRDD.map(x => user(x)).toDS()
    val otherDS = sourceDF.as[user]
    val newDS = spark.createDataset[user](Array(user(5),user(6),user(7)))

    println("===============reduce=============================")
    val i1 = sourceRDD.reduce((x,y) => x+y)
    println(i1)

    println("===============map=============================")
    sourceRDD.map(x => x + 100).take(10).foreach(x => {
      print(x + "\t")
    })
    println()

    println("==================filter==========================")
    sourceRDD.filter(x => x > 5).foreach(x => {
      print(x + "\t")
    })
    println()

    println("===================flatMap=========================")
    sourceRDD.flatMap(x => x.toString + 1).collect.foreach(x => {
      print(x + "\t")
    })
    println()

    println("==================mapPartitions==========================")
    sourceRDD.mapPartitions(set => set.map(x => x+100)).collect.foreach(x => {
      print(x + "\t")
    })
    println()

    println("====================mapPartitionsWithIndex========================")
    sourceRDD.mapPartitionsWithIndex((i,data) =>{
      data.map(x => (x + 100).toString + "分区号：" + i)
    }).collect.foreach(x => {
      print(x + "\t")
    })
    println()

    println("====================sample========================")//采样操作
    sourceRDD.sample(false,0.6,10).collect.foreach(x => {
      print(x + "\t")
    })
    println()

    println("====================union========================")//返回结果的并集
    sourceDS.union(otherDS).collect().foreach(x => {
      print(x + "\t")
    })
    println()

    println("====================intersection========================")//返回结果的交集
    sourceRDD.intersection(newRDD).collect().foreach(x => {
      print(x + "\t")
    })
    println()

    println("====================distinct========================")//将一个RDD中的元素进行去重
    sourceRDD.distinct().collect().foreach(x => {
      print(x + "\t")
    })
    println()

    println("====================groupByKey========================")//通过key进行分组
    sourceRDD.map(("hello",_)).groupByKey().glom().collect().foreach(x => x.foreach(x => {
      print(x + "\t")
    }))
    println()

  }
}
case class user(id:Int)
