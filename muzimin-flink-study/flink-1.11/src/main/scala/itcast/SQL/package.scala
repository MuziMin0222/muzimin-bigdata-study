package itcast

import java.util.concurrent.TimeUnit

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-12-10 10:44
 *       config
 **/
package object SQL {
  val env = StreamExecutionEnvironment.getExecutionEnvironment
  val tenv = StreamTableEnvironment.create(env)

  val ds = env.addSource(
    new RichSourceFunction[(Long, String, Integer)] {
      var flag = true

      override def run(ctx: SourceFunction.SourceContext[(Long, String, Integer)]): Unit = {
        val random = new Random()
        while (flag) {
          ctx.collect((random.nextLong(), random.nextString(5), random.nextInt()))
          TimeUnit.SECONDS.sleep(1)
        }
      }

      override def cancel(): Unit = {
        flag = false
      }
    }
  )
}
