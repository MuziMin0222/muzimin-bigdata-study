package com.MuziMin.source_02.function

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}
import java.util.concurrent.TimeUnit

import com.MuziMin.source_02.bean.MysqlEntity
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-02-25 15:39
 *       ${description}
 **/
class MySqlSource extends RichParallelSourceFunction[MysqlEntity] {
  private val url = "jdbc:mysql://localhost:8017/bigdata"
  private val user = "root"
  private val passWord = "root"
  private val sql = "select id,name,age from flink_source"
  private var ps: PreparedStatement = _
  private var conn: Connection = _
  private var rs: ResultSet = _
  private var flag = true

  override def open(parameters: Configuration): Unit = {
    conn = DriverManager.getConnection(url, user, passWord)
    ps = conn.prepareStatement(sql)
  }

  override def run(sourceContext: SourceFunction.SourceContext[MysqlEntity]): Unit = {
    while (flag) {
      rs = ps.executeQuery()
      while (rs.next()) {
        val id = rs.getInt("id")
        val name = rs.getString("name")
        val age = rs.getInt("age")
        sourceContext.collect(MysqlEntity(id, name, age))
      }
      TimeUnit.SECONDS.sleep(1)
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
