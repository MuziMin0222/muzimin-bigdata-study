package com.MuziMin.source_02

import java.util.Properties

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * @author : 李煌民
 * @date : 2021-02-26 11:34
 *       ${description}
 **/
object KafkaSourceDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val props = new Properties()
    props.setProperty("bootstrap.servers", "hadoop01:9092,hadoop02:9092,hadoop03:9092")
    props.setProperty("group.id", "test")
    //latest从最新的/最后的消息开始消费   earliest最早的/最开始的消息开始消费
    props.setProperty("auto.offset.reset", "latest")
    //后台线程每隔5s检测一下Kafka的分区情况,实现动态分区检测
    props.setProperty("flink.partition-discovery.interval-millis", "5000")
    //自动提交
    props.setProperty("enable.auto.commit", "true")
    //自动提交的时间间隔
    props.setProperty("auto.commit.interval.ms", "2000")

    //开启终端生产者：kafka-console-producer.sh --broker-list hadoop01:9092,hadoop02:9092,hadoop03:9092 --topic test
    val sourceDS = env.addSource(new FlinkKafkaConsumer[String]("test", new SimpleStringSchema(), props))

    sourceDS.print()

    env.execute()
  }
}
