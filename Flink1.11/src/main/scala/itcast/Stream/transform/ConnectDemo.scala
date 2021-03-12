package itcast.Stream.transform

import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.{ConnectedStreams, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-11-05 11:36
 *       ${description}
 **/
object ConnectDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val numberSourceDS = env.addSource(new RichSourceFunction[Int] {
      var flag = true
      var num = 0

      override def run(sourceContext: SourceFunction.SourceContext[Int]): Unit = {
        while (flag) {
          num += 1
          sourceContext.collect(num)
          TimeUnit.SECONDS.sleep(1)
        }
      }

      override def cancel(): Unit = {
        flag = false
      }
    })

    val strSourceDS = env.addSource(new RichSourceFunction[String] {
      var flag = true
      var num = 1

      override def run(sourceContext: SourceFunction.SourceContext[String]): Unit = {
        while (flag) {
          num += 1
          sourceContext.collect("Str:" + num)
          TimeUnit.SECONDS.sleep(1)
        }
      }

      override def cancel(): Unit = {
        flag = false
      }
    })

    //connect只是把两个流合并为一个，但是处理业务逻辑都是按照自己的方法处理，两条流可以共享状态数据
    val connectDS: ConnectedStreams[Int, String] = numberSourceDS.connect(strSourceDS)
    val mapDS = connectDS.map(intNumber => intNumber + "这是一个数字",str => str + "这是一个字符串")
    mapDS.print()
    env.execute()
  }
}
