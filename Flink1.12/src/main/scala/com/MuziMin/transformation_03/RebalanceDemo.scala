package com.MuziMin.transformation_03

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 17:31
 *       ${description}
 **/
object RebalanceDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.fromSequence(1, 100)

    val filterDS = sourceDS
      .filter(
        elem => {
          elem % 2 != 0
        }
      )

    //不经过Rebalance
    val resDS1 = filterDS.map(
      new RichMapFunction[Long, (Long, Int)] {
        override def map(in: Long): (Long, Int) = {
          val subtaskID = getRuntimeContext.getIndexOfThisSubtask
          (in, subtaskID)
        }
      }
    ).keyBy(_._2).sum(0)
    resDS1.print("not rebalance")

    val res2DS = filterDS
      .rebalance
      .map(
        new RichMapFunction[Long, (Long, Int)] {
          override def map(in: Long): (Long, Int) = {
            val subtaskID = getRuntimeContext.getIndexOfThisSubtask
            (in, subtaskID)
          }
        }
      ).keyBy(_._2).sum(0)
    res2DS.print("use rebalance")

    env.execute()
  }
}
