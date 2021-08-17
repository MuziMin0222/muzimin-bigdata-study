package com.MuziMin.sink_04.function

import java.util.Properties

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.Logger

/**
 * @author : 李煌民
 * @date : 2021-05-18 23:10
 *       ${description}
 **/
class MyKafkaSink extends RichSinkFunction[String]{
  val log: Logger = org.slf4j.LoggerFactory.getLogger(this.getClass)
  var props: Properties = _
  var producer: KafkaProducer[String, String] = _

  override def open(parameters: Configuration): Unit = {
    props = new Properties()
    //设置键可以序列化//设置键可以序列化
    props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    //设置值进行序列化
    props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    //设置kafka要连接的服务器
    props.setProperty("bootstrap.servers", "hadoop01:9092")
    //设置topic
    props.setProperty("topic", "test")

    producer = new KafkaProducer[String, String](props)
  }

  override def invoke(value: String, context: SinkFunction.Context): Unit = {
    val record: ProducerRecord[String, String] = new ProducerRecord[String, String]("test", value)
    producer.send(record, getCallBack(value, record))
    producer.flush()
  }

  //  使用异步发送，失败则再发送一次，需配合终止条件
  var failure_count = 0

  private def getCallBack(data: String, record: ProducerRecord[String, String]): Callback = {
    val startTime = System.currentTimeMillis()
    new Callback {
      override def onCompletion(recordMetadata: RecordMetadata, e: Exception): Unit = {
        if (e != null) {
          failure_count += 1
          if (failure_count > 3)
            log.error("Failed to send message to " + data + ", try again")
          producer.send(record, getCallBack(data, record))
        } else {
          val endTime = System.currentTimeMillis()
          log.info(s"msg: $data send success, delay: ${endTime - startTime}ms, offset: ${recordMetadata.offset()}, timeStamp: ${recordMetadata.timestamp()}")
        }
      }
    }
  }

  override def close(): Unit = {
    producer.close()
  }
}
