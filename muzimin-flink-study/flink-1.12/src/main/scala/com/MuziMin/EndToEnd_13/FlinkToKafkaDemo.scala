package com.MuziMin.EndToEnd_13

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.time.Time
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2021-03-03 16:54
 *       ${description}
 **/
object FlinkToKafkaDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)
    env.setParallelism(1)

    //config ck
    env.enableCheckpointing(1000)
    env.setStateBackend(new FsStateBackend("file:///D:/ck"))
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)
    env.getCheckpointConfig.setTolerableCheckpointFailureNumber(10)
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setCheckpointTimeout(60000)
    //    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)

    //config 延迟重启，重启3次，每次重启的间隔是5秒
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, Time.of(5, TimeUnit.SECONDS)))
    //失败率重启  如果1分钟内job失败不超过3次,自动重启,每次重启间隔3s (如果1分钟内程序失败达到3次,则程序退出）
    //env.setRestartStrategy(RestartStrategies.failureRateRestart(3, Time.of(1, TimeUnit.MINUTES),Time.of(3, TimeUnit.SECONDS)))

    val prop = new Properties()
    prop.setProperty("bootstrap.servers", "hadoop01:9092,hadoop02:9092,hadoop02:9092")
    prop.setProperty("group.id", "flink")
    prop.setProperty("auto.offset.reset", "latest")
    prop.setProperty("flink.partition-discovery.interval-millis", "5000")
    prop.setProperty("enable.auto.commit", "true")
    prop.setProperty("auto.commit.interval.ms", "2000")

    val inputDS = env.addSource(new FlinkKafkaConsumer[String]("input_kafka", new SimpleStringSchema(), prop))

    val resDS = inputDS.flatMap(
      new RichFlatMapFunction[String, (String, Int)] {
        override def flatMap(value: String, out: Collector[(String, Int)]): Unit = {
          val valueArr = value.split(" ", -1)
          valueArr.foreach(
            item => {
              if (item == "bug") {
                println("异常产生")
                throw new Exception("异常产生")
              }
              out.collect((item, 1))
            }
          )
        }
      }
    ).keyBy(_._1)
      .sum(1)
      .map(elem => {
        s"${elem._1} : ${elem._2}"
      })

    val props = new Properties
    props.setProperty("bootstrap.servers", "hadoop01:9092,hadoop02:9092,hadoop02:9092")
    props.setProperty("transaction.timeout.ms", "5000")

    resDS.addSink(
      new FlinkKafkaProducer[String](
        "output_kafka",
        new KeyedSerializationSchemaWrapper(new SimpleStringSchema()),
        props,
        FlinkKafkaProducer.Semantic.EXACTLY_ONCE
      )
    )

    env.execute()
  }
}
