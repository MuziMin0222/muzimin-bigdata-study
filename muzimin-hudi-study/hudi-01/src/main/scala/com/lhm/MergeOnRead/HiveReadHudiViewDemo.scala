package com.lhm.MergeOnRead

import java.sql.DriverManager
import java.util.Properties

/**
 * @author : 李煌民
 * @date : 2021-02-19 11:36
 *       ${description}
 **/
object HiveReadHudiViewDemo {
  def main(args: Array[String]): Unit = {

  }

  //merge on read 实时视图查询
  def mergeOnReadRealtimeViewByHive() = {
    // 目标表
    val sourceTable = "test_partition_merge_on_read_rt"

    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val prop = new Properties()
    prop.put("user", "hive")
    prop.put("password", "hive")
    val conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/hj_repl", prop)
    val stmt = conn.createStatement

    val rs = stmt.executeQuery("select * from " + sourceTable)
    val metaData = rs.getMetaData
    val count = metaData.getColumnCount


    while (rs.next()) {
      for (i <- 1 to count) {
        println(metaData.getColumnName(i) + ":" + rs.getObject(i).toString)
      }
      println("-----------------------------------------------------------")
    }

    rs.close()
    stmt.close()
    conn.close()
  }

  //merge on read 读优化视图查询
  def mergeOnReadReadoptimizedViewByHive() = {
    // 目标表
    val sourceTable = "test_partition_merge_on_read_ro"

    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val prop = new Properties()
    prop.put("user", "hive")
    prop.put("password", "hive")
    val conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/hj_repl", prop)
    val stmt = conn.createStatement

    val rs = stmt.executeQuery("select * from " + sourceTable)
    val metaData = rs.getMetaData
    val count = metaData.getColumnCount


    while (rs.next()) {
      for (i <- 1 to count) {
        println(metaData.getColumnName(i) + ":" + rs.getObject(i).toString)
      }
      println("-----------------------------------------------------------")
    }

    rs.close()
    stmt.close()
    conn.close()
  }
}
