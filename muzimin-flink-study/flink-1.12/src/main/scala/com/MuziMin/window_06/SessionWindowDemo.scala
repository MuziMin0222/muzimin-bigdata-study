package com.MuziMin.window_06

import com.MuziMin.window_06.function.CarSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author : 李煌民
 * @date : 2021-02-26 15:31
 *       设置会话超时时间为10s,10s内没有数据到来,则触发上个窗口的计算(前提是上一个窗口得有数据!)
 **/
object SessionWindowDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new CarSource)

    sourceDS.print()

    //需要将CarSource中休息10秒，TimeUnit.SECONDS.sleep(10)
    sourceDS.keyBy(_.intersection)
      .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
      .sum("carNum")
      .print("window")

    env.execute()
  }
}
