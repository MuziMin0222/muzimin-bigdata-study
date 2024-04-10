package com.MuziMin.join_12

import java.time.Duration

import com.MuziMin.join_12.bean.{FactOrderItem, Goods, OrderItem}
import com.MuziMin.join_12.function.{GoodsSource, OrderItemSource}
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.functions.JoinFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author : 李煌民
 * @date : 2021-03-02 17:54
 *       windowJoin -> InnerJoin
 **/
object WindowJoinDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val goodsDS = env.addSource(new GoodsSource)
    val OrderItemDS = env.addSource(new OrderItemSource)

    val goodsWithWatermarkDS = goodsDS.assignTimestampsAndWatermarks(
      WatermarkStrategy.forBoundedOutOfOrderness[Goods](Duration.ofSeconds(5))
        .withTimestampAssigner(new SerializableTimestampAssigner[Goods] {
          override def extractTimestamp(element: Goods, recordTimestamp: Long): Long = System.currentTimeMillis()
        })
    )

    val orderItemWithWatermarkDS = OrderItemDS.assignTimestampsAndWatermarks(
      WatermarkStrategy.forBoundedOutOfOrderness[OrderItem](Duration.ofSeconds(5))
        .withTimestampAssigner(new SerializableTimestampAssigner[OrderItem] {
          override def extractTimestamp(element: OrderItem, recordTimestamp: Long): Long = System.currentTimeMillis()
        })
    )

    val resultDS = goodsWithWatermarkDS
      .join(orderItemWithWatermarkDS)
      .where(_.goodsId)
      .equalTo(_.goodsId)
      .window(TumblingEventTimeWindows.of(Time.seconds(5)))
      .apply(
        new JoinFunction[Goods, OrderItem, FactOrderItem] {
          override def join(first: Goods, second: OrderItem): FactOrderItem = {
            FactOrderItem(first.goodsId, first.goodsName, second.count, second.count * first.goodsPrice)
          }
        }
      )

    resultDS.print()

    env.execute()
  }
}
