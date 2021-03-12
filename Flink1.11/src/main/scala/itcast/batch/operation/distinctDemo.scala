package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 16:50
 *       ${description}
 **/
object distinctDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List(("java", 1), ("spark", 1), ("scala", 1), ("java", 2)))
    sourceDS.distinct(0).print() // 按某一个字段进行去重
    println("-----------")
    sourceDS.distinct().print()  // 按全部进行去重
  }
}
