package itcast.TableApi

import java.util.concurrent.TimeUnit

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.api.{EnvironmentSettings, _}
import org.apache.flink.types.Row

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-12-22 14:58
 *       ${description}
 **/
object JoinDemo {
  def main(args: Array[String]): Unit = {
    val bsEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv: StreamTableEnvironment = StreamTableEnvironment.create(bsEnv, bsSettings)

    val orderDS = bsEnv.addSource(
      new RichSourceFunction[Order] {
        var flag = true
        lazy val idArr = Array(1, 2, 3, 4, 5, 6, 7)
        lazy val random = new Random()

        override def run(ctx: SourceFunction.SourceContext[Order]): Unit = {
          while (flag) {
            ctx.collect(Order(idArr(random.nextInt(idArr.length - 1)), random.nextString(2), random.nextString(20)))
            TimeUnit.SECONDS.sleep(1)
          }
        }

        override def cancel(): Unit = {
          flag = false
        }
      }
    )

    val productDS = bsEnv.addSource(
      new RichSourceFunction[Product] {
        var flag = true
        lazy val idArr: Array[Int] = Array(6, 7, 8, 9, 10, 11)
        lazy val random = new Random()

        override def run(ctx: SourceFunction.SourceContext[Product]): Unit = {
          while (flag) {
            ctx.collect(Product(idArr(random.nextInt(idArr.length - 1)), random.nextString(10)))
            TimeUnit.SECONDS.sleep(1)
          }
        }

        override def cancel(): Unit = {
          flag = false
        }
      }
    )

    val order = tenv.fromDataStream(orderDS)
      .renameColumns($"id" as "id_temp")
    val product = tenv.fromDataStream(productDS)

    order.leftOuterJoin(product, $"id" === $"id_temp")
//      .select($"id_temp", $"name", $"age")
      .toRetractStream[Row]
      .print()

    bsEnv.execute()

  }
}
