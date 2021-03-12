package com.MuziMin.join_12.function

import java.util.concurrent.TimeUnit
import java.util.{Random, UUID}

import com.MuziMin.join_12.bean.OrderItem
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-03-03 14:37
 *       ${description}
 **/
class OrderItemSource extends RichParallelSourceFunction[OrderItem] {
  private var flag = true
  private var random: Random = _

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[OrderItem]): Unit = {
    while (flag) {
      val goodsId = random.nextInt(10) + 1
      val itemId = UUID.randomUUID().toString
      val count = random.nextInt(10) + 1

      sourceContext.collect(OrderItem(itemId, goodsId, count))
      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
