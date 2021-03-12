package com.lhm.atguigu.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WorldCountDemo {
  def main(args: Array[String]): Unit = {

    //定义更新状态方法，参数values为当前批次单词频度，state为往批单词频度
    val updateFunc = (values:Seq[Int],state:Option[Int]) => {
      val currentCount = values.foldLeft(0)(_+_)
      val previousCount = state.getOrElse(0)
      Some(currentCount + previousCount)
    }

    val conf = new SparkConf().setMaster("local[*]").setAppName("WorldCount")
    val context = new StreamingContext(conf,Seconds(3))

    //保存数据的状态，要设置检查点路径
    context.sparkContext.setCheckpointDir("CheckPointDir")

    val MapDStream = context.socketTextStream("localhost",9999).flatMap(_.split(" ")).map((_,1))

    //将数据进行结构转换方便统计
    val stateRes = MapDStream.updateStateByKey[Int](updateFunc)

    stateRes.print()

    context.start()
    context.awaitTermination()
  }
}
