package com.MuziMin.watermarker_07

import java.time.Duration

import com.MuziMin.watermarker_07.bean.OrderWithEventTime
import com.MuziMin.watermarker_07.function.OrderEventTimeSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author : 李煌民
 * @date : 2021-02-26 17:25
 *       ${description}
 **/
object AllowedLatenessDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new OrderEventTimeSource)

    sourceDS.print()

    val laterTag = new OutputTag[OrderWithEventTime]("later")

    val resultDS = sourceDS
      .assignTimestampsAndWatermarks(
        WatermarkStrategy.forBoundedOutOfOrderness[OrderWithEventTime](Duration.ofSeconds(5))
          .withTimestampAssigner(new SerializableTimestampAssigner[OrderWithEventTime] {
            override def extractTimestamp(element: OrderWithEventTime, recordTimestamp: Long): Long = {
              element.eventTime
            }
          })
      )
      .keyBy(_.userId)
      .window(TumblingEventTimeWindows.of(Time.seconds(5)))
      .allowedLateness(Time.seconds(3))
      .sideOutputLateData(laterTag)
      .sum("money")

    val laterDS = resultDS.getSideOutput(laterTag)

    resultDS.print("正常数据：")
    laterDS.print("迟到数据")

    env.execute()
  }
}
