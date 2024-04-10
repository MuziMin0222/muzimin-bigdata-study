package atguigu.flink_sink

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011

object MyKafka {
  private val prop = new Properties()

  prop.setProperty("bootstrap.servers","bd1:9092,bd2:9092,bd3:9093")
  prop.setProperty("group.id","group1")

  def getProducer(topic:String)={
    new FlinkKafkaProducer011[String](topic,new SimpleStringSchema(),prop)
  }
}
