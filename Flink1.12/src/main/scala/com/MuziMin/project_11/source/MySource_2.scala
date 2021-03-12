package com.MuziMin.project_11.source

import java.util.concurrent.TimeUnit
import java.util.{Random, UUID}

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-03-02 09:48
 *       ${description}
 **/
class MySource_2 extends RichParallelSourceFunction[(String, String, Long)] {
  private var flag = true
  private var random: Random = _

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[(String, String, Long)]): Unit = {
    while (flag) {
      val userId = random.nextInt(5).toString
      val orderId = UUID.randomUUID().toString
      val currentTime = System.currentTimeMillis()

      sourceContext.collect((userId, orderId, currentTime))

      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
