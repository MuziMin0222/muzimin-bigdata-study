package com.lhm.Demo

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author : 李煌民
 * @date : 2020-10-30 15:35
 *       ${description}
 **/
object CleanData {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("demo")
    val sc = new SparkContext(conf)
    val sourceRDD = sc.textFile("D:\\res.csv")
  }

}
