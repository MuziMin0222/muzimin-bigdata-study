package com.MuziMin.project_11.source

import java.util.Random
import java.util.concurrent.TimeUnit

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-03-01 16:33
 *       ${description}
 **/
class MySource_1 extends RichParallelSourceFunction[(String, Double)] {
  private var flag = true
  private val categorys = Array("女装", "男装", "图书", "家电", "洗护", "美妆", "运动", "游戏", "户外", "家具", "乐器", "办公")
  private var random: Random = _

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[(String, Double)]): Unit = {
    while (flag) {
      val index = random.nextInt(categorys.length)
      val category = categorys(index)
      val price = random.nextDouble() * 100

      sourceContext.collect((category, price))

      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
