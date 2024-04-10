package com.MuziMin.project_11.function

import org.apache.flink.api.common.functions.AggregateFunction

/**
 * @author : 李煌民
 * @date : 2021-03-01 16:57
 *
 **/
class PriceAggregate() extends AggregateFunction[(String, Double), Double, Double] {
  //初始化累加器
  override def createAccumulator(): Double = 0D

  //把数据累加到累加器上
  override def add(value: (String, Double), accumulator: Double): Double = value._2 + accumulator

  //获取累加结果
  override def getResult(accumulator: Double): Double = accumulator

  //合并各个subtask的结果
  override def merge(a: Double, b: Double): Double = a + b
}
