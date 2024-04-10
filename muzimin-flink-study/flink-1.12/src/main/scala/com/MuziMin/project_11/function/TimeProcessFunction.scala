package com.MuziMin.project_11.function

import org.apache.flink.api.common.state.{MapState, MapStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2021-03-02 10:22
 *
 **/
class TimeProcessFunction(interval: Long) extends KeyedProcessFunction[String, (String, String, Long), Any] {
  //订单和时间
  private var mapState: MapState[String, Long] = _

  override def open(parameters: Configuration): Unit = {
    val mapStateDesc = new MapStateDescriptor[String, Long]("mapState", classOf[String], classOf[Long])
    mapState = getRuntimeContext.getMapState(mapStateDesc)
  }

  override def processElement(value: (String, String, Long), context: KeyedProcessFunction[String, (String, String, Long), Any]#Context, collector: Collector[Any]): Unit = {
    mapState.put(value._2, value._3)

    isFavorable(value._2) match {
      case true => {
        println(s"订单：${value._2}已经好评")
        //移除状态中的数据
        mapState.remove(value._2)
      }
      case false => {
        //没有好评就启动定时任务
        //在该事件的5S后启动定时任务
        context.timerService().registerProcessingTimeTimer(value._3 + interval)
      }
    }
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, (String, String, Long), Any]#OnTimerContext, out: Collector[Any]): Unit = {
    val iter = mapState.iterator()
    while (iter.hasNext) {
      val map = iter.next()
      val orderId = map.getKey
      val orderTime = map.getValue

      if (System.currentTimeMillis() - orderTime >= interval) {
        println(s"orderId：$orderId 该订单已经超时未评价,系统自动给与好评!....")
        iter.remove()
        mapState.remove(orderId)
      }
    }
  }

  override def close(): Unit = super.close()

  //判断是否好评
  def isFavorable(orderId: String): Boolean = {
    val bool = orderId.hashCode % 2 == 0
    bool
  }
}
