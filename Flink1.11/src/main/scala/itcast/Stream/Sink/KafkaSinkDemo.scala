package itcast.Stream.Sink

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper
import org.apache.kafka.clients.producer.ProducerConfig

/**
 * @author : 李煌民
 * @date : 2020-11-06 14:33
 *       ${description}
 **/
object KafkaSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List("aaa", "bbbb", "ccc"))
    val topic = "test"
    val keyedSerialization = new KeyedSerializationSchemaWrapper[String](new SimpleStringSchema())
    val prop = new Properties()
    prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092")

    val flinkProducer = new FlinkKafkaProducer011[String](topic, keyedSerialization, prop)

    sourceDS.addSink(flinkProducer)

    env.execute()
  }
}
