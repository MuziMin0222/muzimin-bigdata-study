package com.MuziMin.window_06

import com.MuziMin.window_06.function.CarSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author : 李煌民
 * @date : 2021-02-26 14:51
 *       每5秒钟统计一次，最近5秒钟内，各个路口通过红绿灯汽车的数量--基于时间的滚动窗口
 **/
object TumblingTimeWindowDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new CarSource)

    val resultDS = sourceDS
      .keyBy(_.intersection) //根据路口分组
      .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .sum("carNum")

    resultDS.print()

    env.execute()
  }
}
