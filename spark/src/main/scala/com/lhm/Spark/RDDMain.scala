package com.lhm.Spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, RangePartitioner, SparkConf, SparkContext}

case class Student(name: String, age: Int, sex: String)

object RDDMain {
  def main(args: Array[String]): Unit = {
    //构建configuration对象
    val conf = new SparkConf().setAppName("GetRDD").set("spark.master", "local[*]");

    //获取上下文对象
    val sc = SparkContext.getOrCreate(conf);

    //获取RDD
    val stu_rdd: RDD[String] = sc.textFile("D:\\code\\workspace_IdeaUi\\Spark\\src\\data\\student.txt")
    //    val newrdd: RDD[Array[String]] = stu_rdd.map(line=> line.split(" "))
    val newrdd = stu_rdd.map(line => {
      val Array(name,age,sex) = line.split(" ");
      Student(name,age.toInt,sex);
    }
    )

    println(newrdd.partitioner);//none

    val pairrdd = newrdd.map(student => (student.name,student))
    println(pairrdd.partitioner)
    println("分区前的分区个数：" + pairrdd.getNumPartitions)

    //具有分析方式
    //1、partitionBy()
    //2、带有shuffle操作的方法
    val hashP = new HashPartitioner(10);
    val RangeP = new RangePartitioner[String,Student](10,pairrdd);
    val partitionerRDD = pairrdd.partitionBy(hashP);
    println("分区后的分区个数" + partitionerRDD.getNumPartitions)
    println(partitionerRDD.partitioner)

    val mapRDD = partitionerRDD.map(x=>x);
    println(mapRDD.partitioner)

    val mvRDD = partitionerRDD.mapValues(x=>x);
    println(mvRDD.partitioner)

    //调用RDD操作


    //结束程序
    sc.stop()
  }

  //
}
