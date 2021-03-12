package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 10:51
 *       ${description}
 **/
object FilterDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(
      List("java", "scala", "spark", "flink")
    )
    val filterDS = sourceDS.filter(
      item => {
        item.length > 4
      }
    )
    filterDS.print()
    /*
    scala
    spark
    flink
     */
  }
}
