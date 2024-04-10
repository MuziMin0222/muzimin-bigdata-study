package itcast.Stream.CheckPoint

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-11-19 18:48
 *       ${description}
 **/
object KafkaConsumerDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val prop = new Properties()
    prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092")
    //设置消费者的隔离级别，默认是读取未提及数据  read_uncommitted
    prop.setProperty(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed")
    val kafkaSource = env.addSource(
      new FlinkKafkaConsumer011[String]("test", new SimpleStringSchema(), prop)
    )
    kafkaSource.print("测试卡夫卡一致性语义结果：")

    env.execute()
  }
}
