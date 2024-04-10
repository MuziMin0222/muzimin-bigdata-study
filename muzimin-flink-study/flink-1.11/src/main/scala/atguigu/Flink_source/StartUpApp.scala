package atguigu.Flink_source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object StartUpApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //获取消费者
    val kafkaConsumer = MyKafkaUtil.getConsumer("flink")

    import org.apache.flink.api.scala._
    val ds = env.addSource(kafkaConsumer)

    ds.print()

    env.execute()
  }
}
