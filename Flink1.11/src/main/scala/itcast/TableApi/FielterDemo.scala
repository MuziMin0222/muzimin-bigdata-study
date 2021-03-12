package itcast.TableApi

import org.apache.flink.table.api.DataTypes
import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:47
 *       ${description}
 **/
object FielterDemo {
  def main(args: Array[String]): Unit = {
    //where/filter
    tb1.filter($"a".cast(DataTypes.INT()) % 2 === 0).toAppendStream[Row].print("filter")
    tb1.where($"a" === "9").toAppendStream[Row].print("where")

    env.execute()
  }
}
