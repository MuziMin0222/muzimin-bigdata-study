package com.MuziMin.join_12

import java.time.Duration

import com.MuziMin.join_12.bean.{FactOrderItem, Goods, OrderItem}
import com.MuziMin.join_12.function.{GoodsSource, OrderItemSource}
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2021-03-03 15:07
 *       ${description}
 **/
object IntervalJoinDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val goodsSourceDS = env.addSource(new GoodsSource)
    val itemSourceDS = env.addSource(new OrderItemSource)

    val goodsWithWatermarkDS = goodsSourceDS.assignTimestampsAndWatermarks(
      WatermarkStrategy.forBoundedOutOfOrderness[Goods](Duration.ofSeconds(5))
        .withTimestampAssigner(new SerializableTimestampAssigner[Goods] {
          override def extractTimestamp(element: Goods, recordTimestamp: Long): Long = System.currentTimeMillis()
        })
    )

    val orderItemWithWatermarkDS = itemSourceDS.assignTimestampsAndWatermarks(
      WatermarkStrategy.forBoundedOutOfOrderness[OrderItem](Duration.ofSeconds(5))
        .withTimestampAssigner(new SerializableTimestampAssigner[OrderItem] {
          override def extractTimestamp(element: OrderItem, recordTimestamp: Long): Long = System.currentTimeMillis()
        })
    )

    val resultDS = goodsWithWatermarkDS
      .keyBy(_.goodsId)
      //join的条件:
      // 条件1.id要相等
      // 条件2. OrderItem的时间戳 - 2 <=Goods的时间戳 <= OrderItem的时间戳 + 1
      .intervalJoin(orderItemWithWatermarkDS.keyBy(_.goodsId))
      .between(Time.seconds(-2), Time.seconds(1))
      .process(
        new ProcessJoinFunction[Goods, OrderItem, FactOrderItem] {
          override def processElement(in1: Goods, in2: OrderItem, context: ProcessJoinFunction[Goods, OrderItem, FactOrderItem]#Context, collector: Collector[FactOrderItem]): Unit = {
            collector.collect(FactOrderItem(in1.goodsId, in1.goodsName, in2.count, in1.goodsPrice * in2.count))
          }
        }
      )

    resultDS.print()

    env.execute()
  }
}
