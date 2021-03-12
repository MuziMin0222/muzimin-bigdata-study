package itcast

import java.util.concurrent.TimeUnit

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, _}
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

import scala.util.Random


/**
 * @author : 李煌民
 * @date : 2020-12-31 16:32
 *
 **/
package object TableApi {
  val env = StreamExecutionEnvironment.getExecutionEnvironment
  env.setParallelism(1)
  env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)

  val sourceDS = env.addSource(
    new RichSourceFunction[CaseClass1] {
      var flag = true
      private val random = new Random()

      override def run(ctx: SourceFunction.SourceContext[CaseClass1]): Unit = {
        while (flag) {
          val res = math.abs(random.nextLong())
          ctx.collect(CaseClass1(random.nextInt(10).toString, random.nextInt(10).toString, random.nextString(5), System.currentTimeMillis() + res))
          TimeUnit.SECONDS.sleep(1)
        }
      }

      override def cancel(): Unit = flag = false
    }
  )

  //创建表的执行环境
  val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
  val tenv: StreamTableEnvironment = StreamTableEnvironment.create(env, bsSettings)

  val tb1 = tenv.fromDataStream[CaseClass1](sourceDS, $"a", $"b", $"c", $"rt".proctime())
}
