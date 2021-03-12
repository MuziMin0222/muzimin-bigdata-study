package com.MuziMin.connectors_05

import java.sql.PreparedStatement

import com.MuziMin.source_02.bean.Order
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.connector.jdbc.{JdbcConnectionOptions, JdbcSink, JdbcStatementBuilder}
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-26 11:11
 *       ${description}
 **/
object JdbcConnectorsDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.fromElements(Order("jdbcDemo", 100, 10000, 987654321))

    sourceDS.addSink(
      JdbcSink.sink(
        "insert into `order` (`id`,`userid`,`money`,`createtime`) VALUES(?,?,?,?)",
        new JdbcStatementBuilder[Order]() {
          override def accept(t: PreparedStatement, u: Order): Unit = {
            t.setString(1, u.id)
            t.setInt(2, u.userId)
            t.setInt(3, u.money)
            t.setLong(4, u.createTime)
          }
        },
        new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
          .withUrl("jdbc:mysql://localhost:8017/bigdata")
          .withDriverName("com.mysql.cj.jdbc.Driver")
          .withUsername("root")
          .withPassword("root")
          .build()
      )
    )

    env.execute()
  }
}
