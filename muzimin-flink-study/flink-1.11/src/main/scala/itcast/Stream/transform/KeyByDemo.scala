package itcast.Stream.transform

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-11-05 11:05
 *       ${description}
 **/
object KeyByDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val prop = new Properties()
    //配置kafka集群地址
    prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092")
    //消费者组
    prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "flink")
    //动态分区检测
    prop.setProperty("flink.partition-discover.interval-millis", "5000")
    //设置K-V的反序列化使用的类
    prop.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    prop.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    //设置默认消费者的偏移量起始值,从最新开始消费
    prop.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    val flinkKafkaConsumer = new FlinkKafkaConsumer011[String]("test",new SimpleStringSchema(),prop)
    val sourceDS = env.addSource(flinkKafkaConsumer)
    sourceDS.flatMap(
      line => {
        line.split(" ")
      }
    )
      .map(
        item => {
          (item,1)
        }
      )
      .keyBy(_._1)
      .sum(1)
      .print()

    env.execute()
  }
}
