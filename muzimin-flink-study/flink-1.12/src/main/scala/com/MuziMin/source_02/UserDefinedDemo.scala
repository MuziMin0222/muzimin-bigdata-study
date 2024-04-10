package com.MuziMin.source_02

import com.MuziMin.source_02.function.MyOrderSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
 * @author : 李煌民
 * @date : 2021-02-25 14:55
 *       随机订单数据
 **/
object UserDefinedDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new MyOrderSource)

    sourceDS.print()

    env.execute()
  }
}
