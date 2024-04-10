package com.MuziMin.state_08

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.state.MapStateDescriptor
import org.apache.flink.streaming.api.scala._

import scala.collection.mutable

/**
 * @author : 李煌民
 * @date : 2021-03-02 11:44
 *       有一个事件流--用户的行为日志,里面有用户id,但是没有用户的详细信息
 *       有一个配置流/规则流--用户信息流--里面有用户的详细的信息
 *       现在要将事件流和配置流进行关联, 得出日志中用户的详细信息,如 (用户id,详细信息, 操作)
 *       那么我们可以将配置流/规则流--用户信息流 作为状态进行广播 (因为配置流/规则流--用户信息流较小)
 **/
object BroadcastStateDemo {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    val sourceDS = env.addSource(new MySource)
    val userDS = env.addSource(new MySQLSource)

    val mapDesc: MapStateDescriptor[String, mutable.Map[String, (String, Int)]] = new MapStateDescriptor[String, mutable.Map[String, (String, Int)]]("mapDesc", classOf[String], classOf[mutable.Map[String, (String, Int)]])

    val broadcastDS = userDS.broadcast(mapDesc)

    val connectDS: BroadcastConnectedStream[(String, String, String, Int), mutable.Map[String, (String, Int)]] = sourceDS.connect(broadcastDS)

    val resultDS = connectDS.process(new MyBroadcastProcessFunction(mapDesc))

    resultDS.print()

    env.execute()
  }
}
