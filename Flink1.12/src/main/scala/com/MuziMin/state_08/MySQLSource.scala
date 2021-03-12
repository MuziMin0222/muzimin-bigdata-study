package com.MuziMin.state_08

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}
import java.util.concurrent.TimeUnit

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}

import scala.collection.mutable

/**
 * @author : 李煌民
 * @date : 2021-03-02 11:09
 *       ${description}
 **/
class MySQLSource extends RichSourceFunction[mutable.Map[String, (String, Int)]] {
  private var flag = true
  private var conn: Connection = _
  private var ps: PreparedStatement = _
  private var rs: ResultSet = _

  override def open(parameters: Configuration): Unit = {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:8017/bigdata", "root", "root")
    ps = conn.prepareStatement("select `userID`,`userName`,`userAge` from `user_info`")
  }

  override def run(sourceContext: SourceFunction.SourceContext[mutable.Map[String, (String, Int)]]): Unit = {
    while (flag) {
      rs = ps.executeQuery()
      val map = new mutable.HashMap[String, (String, Int)]()
      while (rs.next()) {
        val userID = rs.getString("userID")
        val userName = rs.getString("userName")
        val userAge = rs.getInt("userAge")

        map += (userID -> (userName, userAge))
      }
      sourceContext.collect(map)
      TimeUnit.SECONDS.sleep(5)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }

  override def close(): Unit = {
    if (rs != null) rs.close()
    if (ps != null) ps.close()
    if (conn != null) conn.close()
  }
}
