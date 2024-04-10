package itcast.batch.operation

import org.apache.flink.api.java.aggregation.Aggregations
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 16:42
 *       ${description}
 **/
object AggregateDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List(("java", 1), ("spark", 1), ("scala", 1), ("java", 2)))
    //出错
    //sourceDS.groupBy(_._1).aggregate(Aggregations.SUM,1).print()//Aggregate does not support grouping with KeySelector functions, yet
    sourceDS.groupBy(0).aggregate(Aggregations.SUM,1).print()
  }
}
