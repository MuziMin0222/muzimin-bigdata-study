package itcast.TableApi

import org.apache.flink.table.api.concat
import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:48
 *       ${description}
 **/
object AddColumnsDemo {
  def main(args: Array[String]): Unit = {
    //AddColumns
    tb1.addColumns(concat($"c", "_flink") as "col1").toAppendStream[Row].print("addColumns")

    env.execute()
  }
}
