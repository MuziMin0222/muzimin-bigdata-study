package itcast.Stream.window

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, WindowedStream}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow

/**
 * @author : 李煌民
 * @date : 2020-11-09 10:01
 *       ${description}
 **/
object WindowTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List("aaa", "bbb", "ccc", "ddd"))
    val windowDS: WindowedStream[(String, Int), String, TimeWindow] = sourceDS.map((_, 1)).keyBy(_._1)
      //.window(TumblingEventTimeWindows.of(Time.seconds(3)))  //滚动时间窗口
      //.window(SlidingProcessingTimeWindows.of(Time.seconds(5),Time.seconds(1)))   //滑动时间窗口
      //.window(EventTimeSessionWindows.withGap(Time.seconds(10)))    //会话窗口
      .timeWindow(Time.seconds(10)) //一个参数   就是滚动时间窗口，两个参数就是滑动时间窗口
    //.timeWindow(Time.seconds(3),Time.seconds(2))  //两个参数，这是一个滑动时间窗口
    //.countWindow(1)   //一个参数    就是滚动计算窗口
    //.countWindow(3, 1)    //两个参数    就是滑动计算窗口
    windowDS.reduce(
      (r1, r2) => {
        (r1._1,r1._2.min(r2._2))
      }).print()

    env.execute()
  }
}
