package com.MuziMin.cdc_16

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-08-17 22:53
 *       ${description}
 **/
object DataStreamCDCDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

//    val sourceFunction = MySqlSource
//      .builder[String]()
//      .hostname("localhost")
//      .port(8017)
//      .databaseList("bigdata")
//      .tableList("products")
//      .username("root")
//      .password("root")
//      .deserializer(new StringDebeziumDeserializationSchema)
//      .build()
//
//    env.addSource(sourceFunction)
//      .print()

    env.execute()
  }
}
