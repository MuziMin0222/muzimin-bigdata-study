package com.MuziMin.async_14

import java.sql.{Connection, DriverManager, PreparedStatement}

import com.MuziMin.source_02.bean.Order
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.concurrent.Executors
import org.apache.flink.streaming.api.scala.async.{ResultFuture, RichAsyncFunction}

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

/**
 * @author : 李煌民
 * @date : 2021-03-04 10:28
 *       Java-vertx中提供的异步client实现异步IO
 **/
class ASyncIOFunction1 extends RichAsyncFunction[Order, Order] {
  private var conn: Connection = _
  private var ps: PreparedStatement = _

  override def open(parameters: Configuration): Unit = {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:8017/bigdata", "root", "root")
    ps = conn.prepareStatement("SELECT * FROM `order`")
  }


  override def timeout(input: Order, resultFuture: ResultFuture[Order]): Unit = {
    println("input >>" + input + "----------请求超时----------------")
  }

  //定义一个异步回调的上下文
  implicit lazy val executor: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.directExecutor())

  override def asyncInvoke(input: Order, resultFuture: ResultFuture[Order]): Unit = {
    val rs = ps.executeQuery()
    val listBuffer = new mutable.ListBuffer[Order]
    while (rs.next()) {
      val id = rs.getString("id")
      val userid = rs.getInt("userid")
      val money = rs.getInt("money")
      val createTime = rs.getLong("createTime")

      val order = Order(id, userid, money, createTime)

      listBuffer.append(order)
    }

    Future{
      resultFuture.complete(listBuffer)
    }
  }

  override def close(): Unit = {
    if (conn != null) conn.close()
    if (ps != null) conn.close()
  }
}
