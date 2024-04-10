package com.MuziMin.cdc_16

import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource
import com.alibaba.ververica.cdc.debezium.StringDebeziumDeserializationSchema
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-08-17 22:53
 *       ${description}
 * */
object DataStreamCDCDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceFunction = MySQLSource
      .builder()
      .hostname("localhost")
      .port(3306)
      .databaseList("muzimin")
      .tableList("t_book")
      .username("root")
      .password("123456")
      .deserializer(new StringDebeziumDeserializationSchema)
      .build()

    env.addSource(sourceFunction)
      .print()

    env.execute()
  }
}
