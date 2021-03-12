package itcast.TableApi

import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:50
 *       ${description}
 **/
object DropColumnsDemo {
  def main(args: Array[String]): Unit = {
    //DropColumns
    tb1.dropColumns($"c").toAppendStream[Row].print("dropColumns")

    env.execute()
  }
}
