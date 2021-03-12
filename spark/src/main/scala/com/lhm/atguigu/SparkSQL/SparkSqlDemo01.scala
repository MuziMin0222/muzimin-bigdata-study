package com.lhm.atguigu.SparkSQL

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSqlDemo01 {
  def main(args: Array[String]): Unit = {
    //SparkConf,创建配置对象
    val conf = new SparkConf().setAppName("SparkSqlDemo").setMaster("local[*]")

    //创建sparkSQL的环境对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    //读取数据，构建DataFrame
    val df = spark.read.json("data/a.json")

    //展示数据
    df.show()

    //使用SQL来展示数据
//    df.createOrReplaceGlobalTempView("user") //创建的是全局视图
    df.createTempView("user")
    spark.sql("select * from global_temp.user").show

    //释放资源
    spark.stop()
  }
}
