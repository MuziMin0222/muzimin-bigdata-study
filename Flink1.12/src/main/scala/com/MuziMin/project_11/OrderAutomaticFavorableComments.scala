package com.MuziMin.project_11

import com.MuziMin.project_11.function.TimeProcessFunction
import com.MuziMin.project_11.source.MySource_2
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-03-02 10:17
 *       订单在一定时间内没有做出评价，就默认好评
 **/
object OrderAutomaticFavorableComments {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new MySource_2())

    val interval = 5000L   //设置5s后没有好评就默认好评

    sourceDS
      .keyBy(_._1)
      .process(new TimeProcessFunction(interval))

    env.execute()
  }
}
