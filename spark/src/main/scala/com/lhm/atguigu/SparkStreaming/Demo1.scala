package com.lhm.atguigu.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Demo1 {
  def main(args: Array[String]): Unit = {
    //1、初始化spark的配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    //2、初始化SparkStreamingContext,每隔五秒时间采集数据
    val context = new StreamingContext(sparkConf,Seconds(5))

    //3、通过监控端口来创建DSteam，读进来的数据是一行行的
    val lineStream = context.socketTextStream("localhost",9999)

    //4、将每一行进行切分，形成一个个的单词
    val words: DStream[String] = lineStream.flatMap(_.split(" "))

    //5、将每一个单词形成一个二元元组
    val Worldtuple: DStream[(String, Int)] = words.map((_,1))

    //6、通过键相同的值进行相加处理
    val res: DStream[(String, Int)] = Worldtuple.reduceByKey(_ + _)

    //7、打印
    res.print()

    //8、启动SparkStreamingContext
    context.start()
    context.awaitTermination()

  }
}
