package itcast.TableApi

import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:45
 *       ${description}
 **/
object SelectDemo {
  def main(args: Array[String]): Unit = {
    //select
    tb1.select($"a", $"c" as "d").toAppendStream[Row].print("select:")

    env.execute()
  }
}
