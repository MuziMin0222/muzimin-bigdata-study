package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val SourceRDD = sc.makeRDD(1 to 16 , 3)
    val GlomRDD = SourceRDD.glom()
    GlomRDD.collect()foreach(array =>{
      println(array.mkString(","))
    })
  }
}
