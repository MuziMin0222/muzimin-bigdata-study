package com.MuziMin.window_06

import com.MuziMin.window_06.function.CarSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-26 15:23
 *       统计在最近5条消息中,各自路口通过的汽车数量,相同的key每出现5次进行统计--基于数量的滚动窗口
 **/
object TumblingCountWindowDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new CarSource)

    sourceDS.print()

    sourceDS.keyBy(_.intersection)
      .countWindow(5)
      .sum("carNum")
      .print("window")

    env.execute()
  }
}
