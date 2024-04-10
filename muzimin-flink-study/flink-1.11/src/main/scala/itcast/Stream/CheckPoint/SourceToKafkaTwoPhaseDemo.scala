package itcast.Stream.CheckPoint

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala._
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper
import org.apache.kafka.clients.producer.ProducerConfig

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-11-19 18:31
 *       自己定义数据到kafka生产者
 **/
object SourceToKafkaTwoPhaseDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStateBackend(new FsStateBackend("file:///D://ck"))
    env.enableCheckpointing(2000)
    env.getCheckpointConfig.setCheckpointTimeout(60000)
    env.getCheckpointConfig.setFailOnCheckpointingErrors(false)
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)

    //自定义数据
    val sourceDS = env.addSource(
      new RichSourceFunction[String] {
        var flag = true

        override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
          while (flag) {
            ctx.collect(new Random().nextString(100))
            TimeUnit.SECONDS.sleep(1)
          }
        }

        override def cancel(): Unit = {
          flag = false
        }
      }
    )
    sourceDS.print()

    val prop = new Properties()
    prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092")
    //设置连接kafka事务的超时时间，FlinkProducer默认事务超时时间是1h，kafka中默认事务超时时间是15分钟
    prop.setProperty(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 60000 * 15 + "") //设置客户端事务超时时间与kafka保持一致
    //输出到Kafka
    sourceDS.addSink(
      new FlinkKafkaProducer011[String](
        "test",
        new KeyedSerializationSchemaWrapper(new SimpleStringSchema()),
        prop,
        //设置producer的语义，默认是at-least-once
        FlinkKafkaProducer011.Semantic.EXACTLY_ONCE
      )
    )

    env.execute()
  }
}
