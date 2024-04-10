package com.MuziMin.sink_04

import com.MuziMin.sink_04.function.MysqlSink
import com.MuziMin.source_02.function.MyOrderSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-26 10:11
 *       ${description}
 **/
object MysqlSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new MyOrderSource)

    sourceDS.addSink(new MysqlSink)

    env.execute()
  }
}
