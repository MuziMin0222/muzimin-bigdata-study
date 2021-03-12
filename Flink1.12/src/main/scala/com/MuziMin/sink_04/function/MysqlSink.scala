package com.MuziMin.sink_04.function

import java.sql.{Connection, DriverManager, PreparedStatement}

import com.MuziMin.source_02.bean.Order
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}

/**
 * @author : 李煌民
 * @date : 2021-02-26 10:14
 *       ${description}
 **/
class MysqlSink extends RichSinkFunction[Order] {
  private val url = "jdbc:mysql://localhost:8017/bigdata"
  private val user = "root"
  private val passWord = "root"

  private var conn: Connection = _
  private var ps: PreparedStatement = _

  private val sql = "insert into `order` (`id`,`userid`,`money`,`createtime`) VALUES(?,?,?,?)"

  override def open(parameters: Configuration): Unit = {
    conn = DriverManager.getConnection(url, user, passWord)
    ps = conn.prepareStatement(sql)
  }

  override def invoke(value: Order, context: SinkFunction.Context): Unit = {
    ps.setString(1, value.id)
    ps.setInt(2, value.userId)
    ps.setInt(3, value.money)
    ps.setLong(4, value.createTime)

    ps.executeUpdate()
  }

  override def close(): Unit = {
    if (conn != null) conn.close()
    if (ps != null) ps.close()
  }
}
