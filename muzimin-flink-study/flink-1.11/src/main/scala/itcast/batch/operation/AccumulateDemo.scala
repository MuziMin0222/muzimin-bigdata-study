package itcast.batch.operation

import org.apache.flink.api.common.accumulators.IntCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem

/**
 * @author : 李煌民
 * @date : 2020-10-21 16:54
 *       ${description}
 **/
object AccumulateDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List("aaa", "bbb", "ccc", "ddd"))

    var num = 0

    val accDS = sourceDS.map(
      new RichMapFunction[String, (String, Int)] {
        // 创建累加器
        private val counter = new IntCounter()

        override def open(parameters: Configuration): Unit = {
          //注册累加器
          getRuntimeContext.addAccumulator("numLine", counter)
        }

        override def map(in: String): (String, Int) = {
          //累加器加1
          counter.add(1)
          num += 1
          println(num)
          (in, num)
        }
      }
    ).setParallelism(2)
    accDS.writeAsText("D:/acc", FileSystem.WriteMode.OVERWRITE)

    val result = env.execute()
    println("累计器结果：" + result.getAccumulatorResult[Int]("numLine"))
    accDS.print()
  }
}
