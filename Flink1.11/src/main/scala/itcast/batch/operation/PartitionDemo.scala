package itcast.batch.operation

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem

/**
 * @author : 李煌民
 * @date : 2020-10-21 12:07
 *       ${description}
 **/
object PartitionDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    //设置全局并行度为2
    env.setParallelism(2)
    val sourceDS = env.fromCollection(List((1, "lhm"), (2, "lhm"), (3, "luzhen"), (4, "luzhen")))

    val hashDS = sourceDS.partitionByHash(_._2)
    val rangeDS = sourceDS.partitionByRange(_._1)

    //对分区进行排序
    val sortDS = hashDS.sortPartition(_._2,Order.ASCENDING)


    hashDS.writeAsText("D:/hash",FileSystem.WriteMode.OVERWRITE)
    rangeDS.writeAsText("D:/range",FileSystem.WriteMode.OVERWRITE)

    env.execute()
  }
}
