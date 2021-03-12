package com.lhm.atguigu.Cache

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

object MySQLDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //定义连接mysql的参数
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://bd1:3306/sparkTomysql"
    val userName = "root"
    val passwd = "root"

    //创建jdbcRDD
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName(driver)
        DriverManager.getConnection(url, userName, passwd)
      },
      "select * from sprak where id > ? and id < ?",
      0,
      10,
      1,
      r => (r.getInt("id"), r.getString("name"), r.getInt("age"))
    )
    rdd.foreach(println)
  }
}
