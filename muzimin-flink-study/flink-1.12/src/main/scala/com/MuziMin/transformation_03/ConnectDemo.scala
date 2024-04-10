package com.MuziMin.transformation_03

import com.MuziMin.source_02.function.{MyOrderSource, MySqlSource}
import com.MuziMin.source_02.bean.{MysqlEntity, Order}
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.functions.co.CoMapFunction
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 17:02
 *       ${description}
 **/
object ConnectDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val mysqlSourceDS = env.addSource(new MySqlSource)
    val orderSourceDS = env.addSource(new MyOrderSource)

    val connectDS: ConnectedStreams[MysqlEntity, Order] = mysqlSourceDS.connect(orderSourceDS)

    val resDS = connectDS.map(new CoMapFunction[MysqlEntity, Order, String] {
      override def map1(in1: MysqlEntity): String = {
        in1.name
      }

      override def map2(in2: Order): String = {
        in2.id
      }
    })

    resDS.print()

    env.execute()
  }
}
