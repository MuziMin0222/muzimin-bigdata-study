package com.lhm.atguigu.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object RDDQueueDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    val context = new StreamingContext(conf,Seconds(5))

    //1、创建RDD队列
    val rddQueue = new mutable.Queue[RDD[Int]]()
    //2、创建QueueInputStream
    val InputDStream = context.queueStream(rddQueue,oneAtATime = false)
    //3、处理队列中的RDD数据
    val mappedStream = InputDStream.map((_,1))
    val reduceStream = mappedStream.reduceByKey(_ + _)

    reduceStream.print()

    //4、启动任务
    context.start()
    //5、循环创建并向RDD队列中放入RDD
    for(i <- 1 to 5){
      rddQueue += context.sparkContext.makeRDD(1 to 300,10)
      Thread.sleep(2000)
    }
    context.awaitTermination()
  }
}
