package com.MuziMin.sink_04

import java.util.Properties

import com.MuziMin.source_02.function.MyOrderSource
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

/**
 * @author : 李煌民
 * @date : 2021-02-26 14:19
 *       ${description}
 **/
object KafkaSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS = env.addSource(new MyOrderSource)
      .map(_.toString)

    val props = new Properties()
    props.setProperty("bootstrap.servers", "hadoop01:9092,hadoop02:9092,hadoop03:9092")

    //开启终端消费者：kafka-console-consumer.sh --bootstrap-server hadoop01:9092,hadoop02:9092,hadoop02:9092 --topic test
    sourceDS.addSink(new FlinkKafkaProducer[String]("test", new SimpleStringSchema(), props))

    env.execute()
  }
}
