package com.lhm.atguigu.Transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object MakeRDDDemo {
  def main(args: Array[String]): Unit = {
    val spark = new SparkConf().setAppName("NewRDD").setMaster("local[*]")
    val sc = new SparkContext(spark)

    //1、基于内存来进行创建
    //方式一,makeRDD和parallelize的底层实现是一样的
    val ListRDD: RDD[Int] = sc.makeRDD(List(1,2,3,4))
    //方式二
    val ArrayRDD: RDD[String] = sc.parallelize(Array("1","2","3","4"))

    ListRDD.foreach(println)
    ArrayRDD.foreach(println)

    //2、基于外部文件来创建RDD
    //默认情况下，可以读取项目路径，也可以实现其他路径：HDFS  hdfs://ip:9000/路径
    //默认从文件中读取的数据都是字符串类型
    val fileRDD: RDD[String] = sc.textFile("data")
    fileRDD.collect().foreach(println)

    fileRDD.saveAsTextFile("outData")

  }
}
