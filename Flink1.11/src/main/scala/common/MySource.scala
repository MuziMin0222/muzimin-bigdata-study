package common

import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}

import scala.util.Random

/**
 * @author : 李煌民
 * @date : 2020-12-17 13:51
 *       ${description}
 **/
class MySource extends RichSourceFunction[String] {

  var flag = true

  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
    val array = Array("A", "B", "C", "D")
    val random = new Random()
    while (flag) {
      val str = "Test_" + array(random.nextInt(array.length - 1))
      ctx.collect(str)
      TimeUnit.SECONDS.sleep(1)
    }
  }

  override def cancel(): Unit = {
    flag = false
  }
}
