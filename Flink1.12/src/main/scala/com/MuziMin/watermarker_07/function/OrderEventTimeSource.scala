package com.MuziMin.watermarker_07.function

import java.util.concurrent.TimeUnit
import java.util.{Random, UUID}

import com.MuziMin.watermarker_07.bean.OrderWithEventTime
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-02-26 16:14
 *       ${description}
 **/
class OrderEventTimeSource extends RichParallelSourceFunction[OrderWithEventTime] {
  private var random: Random = _
  private var flag = true

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[OrderWithEventTime]): Unit = {
    while (flag) {
      val orderId = UUID.randomUUID().toString
      val userId = random.nextInt(3)
      val money = random.nextInt(1000)
      val eventTime = System.currentTimeMillis() - random.nextInt(15) * 1000

      sourceContext.collect(OrderWithEventTime(orderId, userId, money, eventTime))

      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
