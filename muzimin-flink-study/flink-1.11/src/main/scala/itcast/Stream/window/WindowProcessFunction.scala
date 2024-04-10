package itcast.Stream.window

import java.time.Duration
import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-12-17 13:50
 *       ${description}
 *       测试窗口应用函数
 *       我们有三种最基本的操作窗口内的事件的选项:
 *       像批量处理，ProcessWindowFunction 会缓存 Iterable 和窗口内容，供接下来全量计算；
 *       或者像流处理，每一次有事件被分配到窗口时，都会调用 ReduceFunction 或者 AggregateFunction 来增量计算；
 *       或者结合两者，通过 ReduceFunction 或者 AggregateFunction 预聚合的增量计算结果在触发窗口时， 提供给 ProcessWindowFunction 做全量计算。
 **/
object WindowProcessFunction {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val sourceDS = env.addSource(
      new RichSourceFunction[(String, Long, Int)] {
        var flag = true
        lazy val idArr = Array("A", "B", "C", "D", "E", "F", "G")
        lazy val random = new Random()

        override def run(ctx: SourceFunction.SourceContext[(String, Long, Int)]): Unit = {
          while (flag) {
            ctx.collect((idArr(random.nextInt(idArr.length - 1)), System.currentTimeMillis() + random.nextInt(1000000), random.nextInt(100)))
            TimeUnit.SECONDS.sleep(1)
          }
        }

        override def cancel(): Unit = {
          flag = false
        }
      }
    )
      .assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(1))
        .withTimestampAssigner(new SerializableTimestampAssigner[(String, Long, Int)] {
          override def extractTimestamp(element: (String, Long, Int), recordTimestamp: Long): Long = {
            element._2
          }
        })
      )

    lazy val outPutTag = new OutputTag[(String, Long, Int)]("late")

    val keyedDS = sourceDS.keyBy(_._1)
      .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .allowedLateness(Time.seconds(1))
      .sideOutputLateData(outPutTag)
      .process(
        new ProcessWindowFunction[
          (String, Long, Int), //输入类型
          (String, Long, Int), //输出类型
          String, //键类型
          TimeWindow //窗口类型
        ] {
          //只能计算出当前窗口的最大值
          override def process(key: String, context: Context, elements: Iterable[(String, Long, Int)], out: Collector[(String, Long, Int)]): Unit = {
            var max = 0
            val iter = elements.iterator
            while (iter.hasNext) {
              max = Math.max(iter.next()._3, max)
            }

            out.collect((key, context.window.getEnd, max))
          }
        }
      )
    keyedDS.print("正常数据---》")
    keyedDS.getSideOutput(outPutTag).printToErr("迟到数据")

    env.execute()
  }
}
