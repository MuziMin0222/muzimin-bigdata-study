package itcast.Stream.watermark

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, WindowedStream}
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.flink.util.Collector
import org.apache.kafka.clients.consumer.ConsumerConfig

/**
 * @author : 李煌民
 * @date : 2020-11-16 14:21
 *       ${description}
 **/
object  WaterMarkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //默认程序的并行度是机器的核数，8个并行度，注意在flink程序中如果是多并行度，水印时间是每个并行度比较最小的值作为当前流的watermark
    env.setParallelism(1)
    //设置处理时间为事件时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //生成水印的周期 默认是200ms
    env.getConfig.setAutoWatermarkInterval(200)

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
    //定义topic
    val topic = "test"
    val kafkaConsumer = new FlinkKafkaConsumer011[String](topic, new SimpleStringSchema(), prop)
    val sourceDS: DataStream[String] = env.addSource(kafkaConsumer)

    val mapDS: DataStream[Car] = sourceDS.map(
      item => {
        val itemArr = item.split(",")
        Car(itemArr(0), itemArr(1).trim.toInt, itemArr(2).trim.toLong)
      }
    )

    //添加水印，周期性的 AssignerWithPeriodicWatermarks使用其子类，构造参数：水印运行的延迟时间，泛型是stream中的数据类型，
    val waterMarkDS: DataStream[Car] = mapDS.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[Car](Time.seconds(2)) {
      //水印机制是在Event Time基础上减去一段时间，就是flink运行数据的延迟的访问
      //eventTime是来自数据，flink是不知道eventTime是多少，以及是哪个字段
      //这个方法就是告诉Flink 事件字段是哪一个
      override def extractTimestamp(t: Car): Long = {
        t.timeStame
      }
    })

    //设置窗口，没5秒的滚动窗口
    val windowStream: WindowedStream[Car, String, TimeWindow] = waterMarkDS.keyBy(_.id).window(TumblingEventTimeWindows.of(Time.seconds(5)))
    val windowDS = windowStream.apply(
      new WindowFunction[Car, Car, String, TimeWindow] {
        //input:当前窗口的数据，out：计算结果的收集器
        override def apply(key: String, window: TimeWindow, input: Iterable[Car], out: Collector[Car]): Unit = {
          val car = input.reduce(
            (a1, a2) => {
              //累加出通过的汽车数量，这里的是时间我们不关心
              Car(key, a1.number + a2.number, a2.timeStame)
            }
          )
          //发送计算结果
          out.collect(car)
          val start = window.getStart
          val end = window.getEnd
          val content = input.iterator.mkString(",")
          println(s"窗口开始时间：$start ===== 窗口结束时间：$end ===== 窗口中的内容：$content")
        }
      }
    )
    windowDS.print()

    env.execute()

  }
}
