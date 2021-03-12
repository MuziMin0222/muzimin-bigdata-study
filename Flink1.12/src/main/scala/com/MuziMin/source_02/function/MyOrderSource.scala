package com.MuziMin.source_02.function

import java.util.concurrent.TimeUnit
import java.util.{Random, UUID}

import com.MuziMin.source_02.bean.Order
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-02-25 15:01
 *       ${description}
 **/
class MyOrderSource extends RichParallelSourceFunction[Order] {
  private var flag = true
  private var random: Random = _

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[Order]): Unit = {
    while (flag) {
      val id = UUID.randomUUID().toString
      val userId = random.nextInt(100)
      val money = random.nextInt(10000)
      val createTime = System.currentTimeMillis() - random.nextInt(1000 * 60 * 60 * 24)

      val value = Order(id,userId,money,createTime)

      sourceContext.collect(value)

      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
