package itcast.TableApi

import org.apache.flink.table.api.concat
import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:49
 *       ${description}
 **/
object AddOrReplaceColumnsDemo {
  def main(args: Array[String]): Unit = {
    //AddOrReplaceColumns   b字段将会被c的值_flink给替代
    tb1.addOrReplaceColumns(concat($"c", "_flink") as "c").toAppendStream[Row].print("addOrReplaceColumns")

    env.execute()
  }
}
