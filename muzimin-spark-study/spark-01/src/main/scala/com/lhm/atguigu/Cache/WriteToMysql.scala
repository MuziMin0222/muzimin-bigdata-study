package com.lhm.atguigu.Cache

import java.sql.DriverManager

import org.apache.spark.{SparkConf, SparkContext}

object WriteToMysql {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List(("Female",20), ("Male",21), ("Female",18)))

    //定义连接mysql的参数
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://bd1:3306/sparkTomysql"
    val userName = "root"
    val passwd = "root"

    //这种方式会多次连接spark和mysql,若把connection放到外面，会报错，connection没有实现序列化
//    sourceRDD.foreach {
    //      case (name, age) => {
    //        Class.forName(driver)
    //        val conn = DriverManager.getConnection(url,userName,passwd)
    //        val sql = "insert into sprak(name,age) values(?,?)"
    //        val ps = conn.prepareStatement(sql)
    //        ps.setString(1,name)
    //        ps.setInt(2,age)
    //        ps.executeUpdate()
    //      }
    //    }

    //另外一种方式,有多少个分区就有多少分connection
    sourceRDD.foreachPartition(
      datas => {
        datas.foreach{
          case (name, age) => {
            Class.forName(driver)
            val conn = DriverManager.getConnection(url,userName,passwd)
            val sql = "insert into sprak(name,age) values(?,?)"
            val ps = conn.prepareStatement(sql)
            ps.setString(1,name)
            ps.setInt(2,age)
            ps.executeUpdate()
          }
        }
      }
    )
  }
}
