package com.MuziMin.table_sql_10

import java.time.Duration

import com.MuziMin.source_02.bean.Order
import com.MuziMin.source_02.function.MyOrderSource
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2021-03-01 11:09
 *       每隔5秒统计最近5秒的每个用户的订单总数，订单最大金额，订单最小金额
 **/
object WatermarkerWindowDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv = StreamTableEnvironment.create(env, settings)

    val sourceDS = env.addSource(new MyOrderSource)

    val OrderWithWatermarkDS: DataStream[Order] = sourceDS.assignTimestampsAndWatermarks(
      WatermarkStrategy
        .forBoundedOutOfOrderness[Order](Duration.ofSeconds(5))
        .withTimestampAssigner(new SerializableTimestampAssigner[Order] {
          override def extractTimestamp(element: Order, recordTimestamp: Long): Long = {
            element.createTime
          }
        })
    )

    tenv.createTemporaryView("t_order", OrderWithWatermarkDS, $"id", $"userId", $"money", $"createTime".rowtime())

    val sql =
      s"""
         |select
         |  userId,
         |  count(distinct id),max(money),min(money) from t_order group by userId,tumble(createTime,INTERVAL '5' SECOND)
         |""".stripMargin

    val resTB = tenv.sqlQuery(sql)

    tenv.toRetractStream[Row](resTB).print("sql")

    val OrderTb = tenv.fromDataStream(OrderWithWatermarkDS, $"id", $"userId", $"money", $"createTime".rowtime())

    val resTB1 = OrderTb
      .window(Tumble over 5.second() on $"createTime" as $"tumbleWindow")
      .groupBy($"userId", $"tumbleWindow")
      .select($"userId", $"id".count().distinct(), $"money".max(), $"money".min())

    resTB1.toRetractStream[Row].print("table")

    env.execute()
  }
}
