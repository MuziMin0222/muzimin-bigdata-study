package com.MuziMin.join_12.function

import java.util.Random
import java.util.concurrent.TimeUnit

import com.MuziMin.join_12.bean.Goods
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * @author : 李煌民
 * @date : 2021-03-02 18:52
 *       ${description}
 **/
class GoodsSource extends RichParallelSourceFunction[Goods] {
  private var flag = true
  private var random: Random = _
  private val goodsNameMap = Map(
    1 -> ("小米12", 4890),
    2 -> ("iphone12", 12000),
    3 -> ("MacBookPro", 15000),
    4 -> ("Thinkpad", 9800),
    5 -> ("MeiZu One", 3200),
    6 -> ("Mate 40", 6500))

  override def open(parameters: Configuration): Unit = {
    random = new Random()
  }

  override def run(sourceContext: SourceFunction.SourceContext[Goods]): Unit = {
    while (flag) {
      val goodsId = random.nextInt(6) + 1
      val tuple = goodsNameMap(goodsId)

      sourceContext.collect(Goods(goodsId, tuple._1, tuple._2))
      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
