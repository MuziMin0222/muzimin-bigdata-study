package com.MuziMin.state_08

import org.apache.flink.api.common.state.{BroadcastState, MapStateDescriptor, ReadOnlyBroadcastState}
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction
import org.apache.flink.util.Collector

import scala.collection.mutable

/**
 * @author : 李煌民
 * @date : 2021-03-02 17:07
 *       ${description}
 **/
class MyBroadcastProcessFunction(mapDesc: MapStateDescriptor[String, mutable.Map[String, (String, Int)]]) extends BroadcastProcessFunction[(String, String, String, Int), mutable.Map[String, (String, Int)], (String, String, String, Int, String, Int)] {
  override def processElement(in1: (String, String, String, Int), readOnlyContext: BroadcastProcessFunction[(String, String, String, Int), mutable.Map[String, (String, Int)], (String, String, String, Int, String, Int)]#ReadOnlyContext, collector: Collector[(String, String, String, Int, String, Int)]): Unit = {
    val broadcastState: ReadOnlyBroadcastState[String, mutable.Map[String, (String, Int)]] = readOnlyContext.getBroadcastState(mapDesc)
    val map: mutable.Map[String, (String, Int)] = broadcastState.get("info")

    if (map != null) {
      val userId = in1._1
      val tuple = map.getOrElse(userId, null)
      var name: String = ""
      var age = 0
      if (tuple != null) {
        name = tuple._1
        age = tuple._2
      }

      collector.collect((userId, in1._2, in1._3, in1._4, name, age))
    }
  }

  override def processBroadcastElement(in2: mutable.Map[String, (String, Int)], context: BroadcastProcessFunction[(String, String, String, Int), mutable.Map[String, (String, Int)], (String, String, String, Int, String, Int)]#Context, collector: Collector[(String, String, String, Int, String, Int)]): Unit = {
    val broadcastState: BroadcastState[String, mutable.Map[String, (String, Int)]] = context.getBroadcastState(mapDesc)
    broadcastState.clear()

    broadcastState.put("info", in2)
  }
}
