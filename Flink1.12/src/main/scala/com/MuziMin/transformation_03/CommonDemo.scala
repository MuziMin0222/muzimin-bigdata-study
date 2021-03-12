package com.MuziMin.transformation_03

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 16:27
 *       ${description}
 **/
object CommonDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.socketTextStream("localhost", 9999)

    sourceDS.print("source")

    val resultDS = sourceDS
      .flatMap(_.split(" "))
      .filter(!_.equals("bug"))
      .map((_, 1))
      .keyBy(_._1)
      .reduce((a, b) => {
        (a._1, a._2 + b._2)
      })

    resultDS.print()

    env.execute()
  }
}
