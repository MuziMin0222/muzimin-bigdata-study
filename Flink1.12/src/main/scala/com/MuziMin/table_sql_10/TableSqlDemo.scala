package com.MuziMin.table_sql_10

import com.MuziMin.table_sql_10.bean.Order
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2021-03-01 10:20
 *       将DataStream数据转Table和View然后使用sql进行统计查询
 **/
object TableSqlDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv = StreamTableEnvironment.create(env, settings)

    val orderA = env.fromCollection(List(
      Order(1L, "beer", 3),
      Order(1L, "diaper", 4),
      Order(3L, "rubber", 2)
    ))

    val orderB = env.fromCollection(List(
      Order(2L, "pen", 3),
      Order(2L, "rubber", 3),
      Order(4L, "beer", 1)
    ))

    val tableA = tenv.fromDataStream(orderA, $"id", $"name", $"money")
    tenv.createTemporaryView("tableB", orderB, $"id", $"name", $"money")

    val sql =
      s"""
         |select * from $tableA where id > 2
         |union all
         |select * from tableB where id > 1
         |""".stripMargin

    val resultTB = tenv.sqlQuery(sql)

    resultTB.toAppendStream[Order].print("方式一：")
    tenv.toAppendStream[Order](resultTB).print("方式二：")

    env.execute()
  }
}
