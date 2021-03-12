package com.MuziMin.window_06

import com.MuziMin.window_06.function.CarSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-26 15:28
 *       统计在最近5条消息中,各自路口通过的汽车数量,相同的key每出现3次进行统计--基于数量的滑动窗口
 **/
object SlidingCountWindowDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new CarSource)

    sourceDS.print()

    sourceDS.keyBy(_.intersection)
      .countWindow(5, 3)
      .sum("carNum")
      .print("window")

    env.execute()
  }
}
