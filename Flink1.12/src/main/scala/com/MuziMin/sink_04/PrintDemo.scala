package com.MuziMin.sink_04

import com.MuziMin.source_02.function.MyOrderSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 18:22
 *       ${description}
 **/
object PrintDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new MyOrderSource)

    sourceDS.print("print")
    sourceDS.printToErr("printToErr")

    //一个并行度输出为文件
    sourceDS.writeAsText("aaa",WriteMode.OVERWRITE).setParallelism(1)

    //>1的并行度输出为文件夹
    sourceDS.writeAsText("bbb",WriteMode.OVERWRITE).setParallelism(2)

    env.execute()
  }
}
