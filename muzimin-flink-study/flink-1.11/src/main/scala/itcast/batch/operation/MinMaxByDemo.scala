package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-21 14:27
 *       ${description}
 **/
object MinMaxByDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List((4,"Chinese",90),(5,"Math",100),(6,"English",50),(1,"Chinese",10),(2,"Math",15),(3,"English",20)))
    val groupDS = sourceDS.groupBy(1)
    groupDS.min(2).print()
    println("--------------")
    groupDS.minBy(2).print()
  }
}
