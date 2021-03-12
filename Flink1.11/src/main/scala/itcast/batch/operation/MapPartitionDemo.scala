package itcast.batch.operation

import itcast.batch.bean.Student
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-16 16:30
 *       ${description}
 **/
object MapPartitionDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val sourceDS = env.fromCollection(List("1,lhm", "2,lz", "3,gj"))

    val stuDS = sourceDS.map(
      line => {
        val arr = line.split(",")
        Student(arr(0).toInt, arr(1))
      }
    )
    stuDS.print()

    val stuDS2 = sourceDS.mapPartition(
      //todo:在一个分区内做一些昂贵的操作，比如开启连接
      itea => { //itea是一个迭代器，将一个分区中的数据都放入迭代器中
        itea.map(
          item => {
            val arr = item.split(",")
            val id = arr(0).toInt
            val name = arr(1)
            Student(id, name)
          }
        )
      }
      //todo：比如关闭连接
    )
    stuDS2.print()
//    env.execute("demo")
  }

}
