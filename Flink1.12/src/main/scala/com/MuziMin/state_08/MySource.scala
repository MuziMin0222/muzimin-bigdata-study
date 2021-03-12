package com.MuziMin.state_08

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.{Date, Random}

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-03-02 10:43
 *       userID, eventTime, eventType, productID
 **/
class MySource extends RichParallelSourceFunction[(String, String, String, Int)] {
  private var isRunning = true
  private var random: Random = _
  private var df: SimpleDateFormat = _

  override def open(parameters: Configuration): Unit = {
    random = new Random()
    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  }

  override def run(sourceContext: SourceFunction.SourceContext[(String, String, String, Int)]): Unit = {
    while (isRunning) {
      val id = random.nextInt(4) + 1
      val user_id = s"user_$id"
      val eventTime = df.format(new Date())
      val eventType = s"type_${random.nextInt(3)}"
      val productId = random.nextInt(4)

      sourceContext.collect((user_id, eventTime, eventType, productId))

      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }
}
