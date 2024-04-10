package com.lhm.atguigu.SparkSQL

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object UDFDemo {
  def main(args: Array[String]): Unit = {
    //SparkConf,创建配置对象
    val conf = new SparkConf().setAppName("SparkSqlDemo").setMaster("local[*]")

    //创建sparkSQL的环境对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //转换之前导入隐式转换规则，spark不是包名，而是sparkSession的变量名
    import spark.implicits._

    //创建DataFrame
    val dataFrame = spark.read.json("data/a.json")

    //注册udf,第一个参数是函数名，第二个参数是函数的具体操作
    spark.udf.register("addName",(s:String) => "Name:" + s)

    dataFrame.createTempView("user")

    spark.sql("select addName(name) from user").show
  }
}
