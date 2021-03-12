package itcast.batch.operation

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-21 11:33
 *       ${description}
 **/
object RebalanceDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.generateSequence(1, 100)

    val newMapDs = sourceDS.map(
      new RichMapFunction[Long, (Long, Long)] {
        //自己定义map逻辑
        override def map(in: Long): (Long, Long) = {
          //获取当前任务的编号，以此来代表分区
          val long: Long = getRuntimeContext.getIndexOfThisSubtask
          (long, in)
        }
      }
    )

    //使用rebalance来将数据进行数据倾斜问题
    val rebalanceDS = newMapDs.rebalance()

    sourceDS.print()
    newMapDs.print()
    rebalanceDS.print()
  }
}
