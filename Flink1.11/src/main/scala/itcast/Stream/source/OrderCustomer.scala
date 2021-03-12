package itcast.Stream.source

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-10-26 10:21
 *       ${description}
 *       订单案例自定义source小练习
 **/
/**
 * 订单样例类
 *
 * @param orderId  ：订单ID
 * @param uid      ：用户ID
 * @param payOrder ：订单金额
 * @param time     ：付款时间
 */
case class Order(orderId: String, uid: String, payOrder: String, time: String)

object OrderCustomer {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.addSource(new RichParallelSourceFunction[Order]() {
      var isRunning = true

      override def run(sourceContext: SourceFunction.SourceContext[Order]): Unit = {
        while (isRunning) {
          val orderId = UUID.randomUUID().toString
          val userId = Random.nextInt(2).toString
          val payOrder = Random.nextInt(100).toString
          val time = System.currentTimeMillis().toString
          sourceContext.collect(Order(orderId, userId, payOrder, time))
          //每隔一秒执行一次
          TimeUnit.SECONDS.sleep(1)
        }
      }

      override def cancel(): Unit = {
        isRunning = false
      }
    }).setParallelism(2)
    sourceDS.print()
    env.execute()
  }
}
