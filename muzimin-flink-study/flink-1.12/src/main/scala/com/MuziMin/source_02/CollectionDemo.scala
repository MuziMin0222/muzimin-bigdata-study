package com.MuziMin.source_02

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
/**
 * @author : 李煌民
 * @date : 2021-02-25 13:36
 *       ${description}
 **/
object CollectionDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val elementDS = env.fromElements("hadoop spark flink", "hadoop spark flink")

    val collectionDS = env.fromCollection(Array("hadoop spark flink", "hadoop spark flink"))

    val sequenceDS = env.fromSequence(1,10)

    elementDS.print("element")
    collectionDS.print("collection")
    sequenceDS.print("sequence")

    env.execute()
  }
}
