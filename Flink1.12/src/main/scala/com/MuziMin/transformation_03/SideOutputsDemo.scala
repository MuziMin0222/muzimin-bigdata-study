package com.MuziMin.transformation_03

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2021-02-25 17:20
 *      将一个流中的数据拆为双数一个流，奇数一个流
 **/
object SideOutputsDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.fromSequence(1, 100)

    val oddTag = new OutputTag[Long]("奇数")
    val eventTag = new OutputTag[Long]("双数")

    val resultDS = sourceDS.process(
      new ProcessFunction[Long, String] {
        override def processElement(i: Long, context: ProcessFunction[Long, String]#Context, collector: Collector[String]): Unit = {
          val bool = i % 2 == 0
          bool match {
            case true => {
              context.output(eventTag, i)
            }
            case false => {
              context.output(oddTag, i)
            }
          }
        }
      }
    )

    resultDS.getSideOutput(oddTag).print("奇数")
    resultDS.getSideOutput(eventTag).print("双数")

    env.execute()
  }
}
