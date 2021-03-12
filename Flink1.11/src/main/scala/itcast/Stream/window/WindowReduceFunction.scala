package itcast.Stream.window

import java.time.Duration
import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-12-18 10:52
 *       ${description}
 **/
object WindowReduceFunction {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val sourceDS = env.addSource(new RichSourceFunction[(String, Long, Int)] {
      var flag = true
      lazy val idArr = Array("A", "B", "C", "D", "E", "F", "G")
      lazy val random = new Random()

      override def run(ctx: SourceFunction.SourceContext[(String, Long, Int)]): Unit = {
        while (flag) {
          ctx.collect((idArr(random.nextInt(idArr.length - 1)), System.currentTimeMillis()/1000 + random.nextInt(1000000), random.nextInt(100)))
          TimeUnit.SECONDS.sleep(1)
        }
      }

      override def cancel(): Unit = {
        flag = false
      }
    })


    sourceDS
      .assignTimestampsAndWatermarks(
        WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(1))
          .withTimestampAssigner(new SerializableTimestampAssigner[(String, Long, Int)] {
            override def extractTimestamp(element: (String, Long, Int), recordTimestamp: Long): Long = {
              element._2
            }
          })
      )
      .keyBy(_._1)
      .window(TumblingEventTimeWindows.of(Time.seconds(5)))
      //指定 允许的延迟(allowed lateness) 的间隔，在这个间隔时间内，延迟的事件将会继续分配给窗口（同时状态会被保留）
      .allowedLateness(Time.seconds(100))
      .reduce(
        new ReduceFunction[(String, Long, Int)] {
          override def reduce(value1: (String, Long, Int), value2: (String, Long, Int)): (String, Long, Int) = {
            if (value1._3 > value2._3) {
              value1
            } else {
              value2
            }
          }
        },
        new ProcessWindowFunction[(String, Long, Int), (String, Long, Int), String, TimeWindow] {
          override def process(key: String, context: Context, elements: Iterable[(String, Long, Int)], out: Collector[(String, Long, Int)]): Unit = {
            //请注意elements.iterator 将只包含一个读数 –> ReduceFunction 计算出的预先汇总的最大值。
            val maxTuple = elements.iterator.next()
            out.collect((key, context.window.getEnd, maxTuple._3))
          }
        }
      ).print()

    env.execute()
  }
}
