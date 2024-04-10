package itcast.TableApi

import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:51
 *       ${description}
 **/
object RenameColumnsDemo {
  def main(args: Array[String]): Unit = {
    //RenameColumns
    tb1.renameColumns($"a" as "a_temp", $"c" as "c_temp").select($"c_temp", $"a_temp").toAppendStream[Row].print("renameColumns")
    env.execute()
  }
}
