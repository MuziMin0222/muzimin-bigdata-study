package itcast.TableApi

import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 17:20
 *       ${description}
 **/
object GroupByAggregationDemo {
  def main(args: Array[String]): Unit = {
    //GroupBy Aggregation $"b.count().distinct()  相当于sql中的count（distinct b）
    tb1.groupBy($"a")
      .select($"a", $"b".count().distinct().as("sum_res"))
      .toRetractStream[Row]
      .print("GroupBy")

    env.execute()
  }
}
