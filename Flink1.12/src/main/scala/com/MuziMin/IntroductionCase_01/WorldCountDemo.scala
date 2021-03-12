package com.MuziMin.IntroductionCase_01

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 11:05
 *       ${description}
 **/
object WorldCountDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.fromElements("hadoop spark", "hadoop spark", "hadoop", "MuziMin")

    val resDS = sourceDS
      .flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(_._1)
      .reduce((a, b) => {
        (a._1, a._2 + b._2)
      })

    resDS.print()

    env.execute()
  }
}