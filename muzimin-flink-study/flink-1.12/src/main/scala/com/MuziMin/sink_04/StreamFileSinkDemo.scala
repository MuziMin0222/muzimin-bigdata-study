package com.MuziMin.sink_04

import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
import org.apache.flink.streaming.api.functions.sink.filesystem.{OutputFileConfig, StreamingFileSink}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @author : 李煌民
 * @date : 2021-03-04 10:54
 *       ${description}
 **/
object StreamFileSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    //config ck
    env.enableCheckpointing(1000)
    env.setStateBackend(new FsStateBackend("file:///D:/ck"))
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)
    env.getCheckpointConfig.setTolerableCheckpointFailureNumber(10)
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setCheckpointTimeout(60000)

    val sourceDS = env.socketTextStream("localhost",9999)

    sourceDS.print()

    val config = OutputFileConfig
      .builder()
      .withPartPrefix("prefix") //设置文件前缀
      .withPartSuffix(".txt") //设置文件后缀
      .build()

    val fileSink = StreamingFileSink
      .forRowFormat(new Path("hdfs://hadoop01:9000/flink_stream_file_sink"), new SimpleStringEncoder[String]("UTF-8"))
      .withRollingPolicy(
        DefaultRollingPolicy
          .builder()
          .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))    //每隔15分钟生成一个新文件
          .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))   //每隔5分钟没有新数据到来,也把之前的生成一个新文件
          .withMaxPartSize(1024 * 1024 * 1024)
          .build()
      )
      .withBucketAssigner(new DateTimeBucketAssigner("yyyyMMdd"))
      .withOutputFileConfig(config)
      .build()

    sourceDS.addSink(fileSink)

    env.execute()
  }
}
