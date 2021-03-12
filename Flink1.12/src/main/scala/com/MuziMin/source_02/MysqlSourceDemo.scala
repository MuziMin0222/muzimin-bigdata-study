package com.MuziMin.source_02

import com.MuziMin.source_02.function.MySqlSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 15:11
 *       ${description}
 **/
object MysqlSourceDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new MySqlSource)

    sourceDS.print()

    env.execute()
  }
}
