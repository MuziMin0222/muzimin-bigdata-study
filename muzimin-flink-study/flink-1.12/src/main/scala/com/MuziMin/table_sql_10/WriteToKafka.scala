package com.MuziMin.table_sql_10

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2021-03-01 15:17
 *       从Kafka:input_kafka主题消费数据并生成Table,然后过滤出状态为success的数据再写回到Kafka:output_kafka主题
 **/
object WriteToKafka {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv = StreamTableEnvironment.create(env, settings)

    //取数据
    tenv.executeSql(
      s"""
         |CREATE TABLE input_kafka (
         |  `user_id` BIGINT,
         |  `page_id` BIGINT,
         |  `status` STRING
         |) WITH (
         |  'connector' = 'kafka',
         |  'topic' = 'input_kafka',
         |  'properties.bootstrap.servers' = 'hadoop01:9092,hadoop02:9092,hadoop03:9092',
         |  'properties.group.id' = 'test',
         |  'scan.startup.mode' = 'latest-offset',
         |  'format' = 'json'
         |)
         |""".stripMargin)

    //处理数据
    val resTB = tenv.sqlQuery(
      s"""
         |select * from input_kafka where status = 'success'
         |""".stripMargin)

    resTB.toAppendStream[Row].print()

    //插入数据
    tenv.executeSql(
      s"""
         |CREATE TABLE output_kafka (
         |  `user_id` BIGINT,
         |  `page_id` BIGINT,
         |  `status` STRING
         |) WITH (
         |  'connector' = 'kafka',
         |  'topic' = 'output_kafka',
         |  'properties.bootstrap.servers' = 'hadoop01:9092,hadoop02:9092,hadoop03:9092',
         |  'format' = 'json',
         |  'sink.partitioner' = 'round-robin'
         |)
         |""".stripMargin)

    tenv.executeSql(s"insert into output_kafka select * from $resTB")

    env.execute()
  }
}

/*
kafka-topics.sh --create --bootstrap-server hadoop01:9092,hadoop02:9092,hadoop03:9092 --topic input_kafka --replication-factor 1 --partitions 1
kafka-topics.sh --create --bootstrap-server hadoop01:9092,hadoop02:9092,hadoop03:9092 --topic output_kafka --replication-factor 1 --partitions 1
kafka-console-producer.sh --broker-list hadoop01:9092,hadoop02:9092,hadoop03:9092 --topic input_kafka
kafka-console-consumer.sh --bootstrap-server hadoop01:9092,hadoop02:9092,hadoop03:9092 --topic output_kafka

{"user_id": "1", "page_id":"1", "status": "success"}
{"user_id": "1", "page_id":"1", "status": "success"}
{"user_id": "1", "page_id":"1", "status": "fail"}
 */
