package com.lhm.atguigu.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WindowWorldCount {
  def main(args: Array[String]): Unit = {
    //定义更新状态方法,values是当前批次的单词频度，state是往批单词频度
    val updateFunc = (values:Seq[Int],state:Option[Int]) => {
      val currentCount = values.foldLeft(0)(_ + _)
      val previousCount = state.getOrElse(0)
      Some(currentCount + previousCount)
    }

    val conf = new SparkConf().setAppName("Window").setMaster("local[*]")
    val context = new StreamingContext(conf,Seconds(3))

    context.sparkContext.setCheckpointDir("CheckPointDir")

    val SocketDStream = context.socketTextStream("localhost",9999)

    //窗口大小应该是采集周期的整数倍，窗口滑动的步长也应该采集周期的整数倍
    //以三个采集周期为一个窗口，每次滑动两个采集周期
    val WindowDStream = SocketDStream.window(Seconds(9),Seconds(6))

    val ReduceDStream = WindowDStream.flatMap(_.split(" ")).map((_ , 1)).reduceByKey(_ + _)

    ReduceDStream.print()

    context.start()
    context.awaitTermination()
  }
}
