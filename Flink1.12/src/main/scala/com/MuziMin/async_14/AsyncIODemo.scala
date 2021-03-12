package com.MuziMin.async_14

import java.util.concurrent.TimeUnit

import com.MuziMin.source_02.function.MyOrderSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-03-04 10:20
 *       ${description}
 **/
object AsyncIODemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new MyOrderSource)

    val resDS = AsyncDataStream.unorderedWait(
      sourceDS,
      new ASyncIOFunction1,
      1,
      TimeUnit.SECONDS
    )
    resDS.print()

    env.execute()
  }
}
