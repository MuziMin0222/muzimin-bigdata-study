package com.MuziMin.source_02

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @author : 李煌民
 * @date : 2021-02-25 14:51
 *       ${description}
 **/
object SocketSourceDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    //在WSL中运行nc -lk 9999
    //如果没有先安装nc
    val socketDS = env.socketTextStream("localhost",9999)

    socketDS.print()

    env.execute()
  }
}
