package itcast.Stream.window

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author : 李煌民
 * @date : 2020-12-18 14:54
 *       ${description}
 **/
object WindowProcessFunction2 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val sourceDS = env.socketTextStream("127.0.0.1", 9999)

    sourceDS
      .flatMap(
        _.toLowerCase().split(" ") filter (_.nonEmpty)
      )
      .map((_, 1))
      .keyBy(_._1)
      .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .sum(1)
      .print()

    env.execute()
  }
}
