package com.MuziMin.project_11

import com.MuziMin.project_11.function.{FinalResultWindowProcess, PriceAggregate, WindowResult}
import com.MuziMin.project_11.source.MySource_1
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger

/**
 * @author : 李煌民
 * @date : 2021-03-01 16:31
 *       1、实时计算当天零点截止到当前时间的销售总额
 *       2、计算出各个分类销售的Top3
 *       3、每秒钟更新一次统计结果
 **/
object DoubleElevenBigScreem {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new MySource_1)

    //每隔1s聚合一下截止到当前时间的各个分类的销售总金额
    val tempAggRes = sourceDS
      .keyBy(_._1)
      //定义大小为一天的窗口,第二个参数表示中国使用的UTC+08:00时区比UTC时间早
      .window(TumblingProcessingTimeWindows.of(Time.days(1), Time.hours(8)))
      //1s触发一次
      .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(1)))
      .aggregate(new PriceAggregate, new WindowResult)

    tempAggRes.keyBy(_.dateTime)
      .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
      .process(new FinalResultWindowProcess())

    env.execute()
  }
}
