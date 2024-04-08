package com.muzimin.trxx

import java.sql.DriverManager

/**
 * @author: 李煌民
 * @date: 2024-04-08 13:06
 *        ${description}
 * */
object GaussDbDemo {
  def main(args: Array[String]): Unit = {
    Class.forName("com.huawei.gauss200.jdbc.Driver")
    val connection = DriverManager.getConnection("jdbc:gaussdb://172.18.136.188:8000/trdb", "tongrui", "Bigdata123@")
    println(connection.prepareStatement("create database ods").executeUpdate())
  }

}
