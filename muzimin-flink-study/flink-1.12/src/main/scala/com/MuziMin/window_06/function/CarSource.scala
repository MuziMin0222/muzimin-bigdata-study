package com.MuziMin.window_06.function

import java.util.Random
import java.util.concurrent.TimeUnit

import com.MuziMin.window_06.bean.CarEntity
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}


/**
 * @author : 李煌民
 * @date : 2021-02-26 14:52
 *       ${description}
 **/
class CarSource extends RichParallelSourceFunction[CarEntity] {
  private var flag = true
  private var random: Random = _

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[CarEntity]): Unit = {
    while (flag) {
      sourceContext.collect(CarEntity(random.nextInt(2), random.nextInt(10)))
      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
