package atguigu.Flink_source

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011

object MyKafkaUtil {
  val prop = new Properties()

  prop.setProperty("bootstrap.servers","bd1:9092")
  prop.setProperty("group.id","group1")

  //消费者
  def getConsumer(topic:String)={
    new FlinkKafkaConsumer011[String](topic,new SimpleStringSchema(),prop)
  }
}
