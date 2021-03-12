package com.MuziMin.watermarker_07

import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date

import com.MuziMin.watermarker_07.bean.OrderWithEventTime
import com.MuziMin.watermarker_07.function.OrderEventTimeSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.RichWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2021-02-26 15:50
 *       通过下面验证，watermarker只是取时间的整数点
 **/
object WaterMarkerDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new OrderEventTimeSource)

    sourceDS
      .map(
        new RichMapFunction[OrderWithEventTime, (Int, Int, String)] {
          private var format: SimpleDateFormat = _

          override def open(parameters: Configuration): Unit = {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          }

          override def map(value: OrderWithEventTime): (Int, Int, String) = {
            (value.userId, value.money, format.format(new Date(value.eventTime)))
          }
        }
      )
      .print()

    sourceDS
      .assignTimestampsAndWatermarks(
        WatermarkStrategy
          .forBoundedOutOfOrderness[OrderWithEventTime](Duration.ofSeconds(5))
          .withTimestampAssigner(new SerializableTimestampAssigner[OrderWithEventTime] {
            override def extractTimestamp(element: OrderWithEventTime, recordTimestamp: Long): Long = {
              element.eventTime
            }
          })
      )
      .map(elem => {
        (elem.userId, elem.money)
      })
      .keyBy(_._1)
      .window(TumblingEventTimeWindows.of(Time.seconds(5)))
      .apply(
        new RichWindowFunction[(Int, Int), String, Int, TimeWindow] {
          private var format: SimpleDateFormat = _

          override def open(parameters: Configuration): Unit = {
            format = new SimpleDateFormat("HH:mm:ss")
          }

          override def apply(key: Int, window: TimeWindow, input: Iterable[(Int, Int)], out: Collector[String]): Unit = {
            val windowStart = format.format(new Date(window.getStart))
            val windowEnd = format.format(new Date(window.getEnd))

            val moneyRes = input.reduce((a, b) => {
              (a._1, a._2 + b._2)
            })

            out.collect(s"money：$moneyRes === 窗口开始时间：$windowStart === 窗口结束时间：$windowEnd")
          }
        }
      )
      .print("waterMark")

    env.execute()
  }
}
