package itcast.TableApi

import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2020-12-30 09:58
 *       ${description}
 **/
object GroupByWindowDemo {
  def main(args: Array[String]): Unit = {

    //GroupBy Window Aggregation
    tb1.window(Tumble over 5.seconds() on $"rt" as "w")
      .groupBy($"a", $"w")
      .select($"a", $"w".start, $"w".end, $"b".count().distinct() as "b_count")
      .toRetractStream[Row]
      .print("group by window")

    env.execute()
  }
}
