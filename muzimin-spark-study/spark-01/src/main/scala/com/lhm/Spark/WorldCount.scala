package com.lhm.Spark

import org.apache.spark.{SparkConf, SparkContext}

/*
词频统计
 */
object WorldCount {
  def main(args: Array[String]): Unit = {
    //1、设置SparkConf并设置APP名称和设置Master
    val conf = new SparkConf().setAppName("worldCount").setMaster("local[*]")
    //2、创建sparkContext，该对象是提交SparkAPP的入口
    val sc = SparkContext.getOrCreate(conf)

    //3、使用sc创建RDD并执行相应的transformation和action
    val TextFileRDD = sc.textFile("D:\\code\\workspace_IdeaUi\\Spark\\src\\main\\resource\\log4j.properties")
    //RDD操作
    val resRDD = TextFileRDD.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _ ,1)

    resRDD.foreach(println)

    sc.stop()
  }
}
