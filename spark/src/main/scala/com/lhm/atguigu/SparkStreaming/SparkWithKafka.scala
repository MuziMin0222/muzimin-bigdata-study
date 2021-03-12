package com.lhm.atguigu.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkWithKafka {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkWithKafka")
    val context = new StreamingContext(conf, Seconds(5))

    //从kafka中进行采集数据
    val KafkaDStream = KafkaUtils.createStream(
      context, //SparkStreaming的context对象
      "bd1:2181", //zookeeper的集群路径
      "SparkStreaming", //kafka中的topic名称
      Map("SparkStreaming" -> 3) //指定topic中的名称
    )
    val res = KafkaDStream.flatMap(t => t._2.split(" ")).map((_,1)).reduceByKey(_ + _)

    //打印结果
    res.print()

    //启动采集器
    context.start()
    //Driver等待采集器的执行
    context.awaitTermination()
  }
}
