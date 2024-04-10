package itcast.TableApi.TableFunction

import itcast.TableApi._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.api.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2021-01-06 16:30
 *       ${description}
 **/
object Demo3 {
  def main(args: Array[String]): Unit = {
    //    tb1
    //      .addOrReplaceColumns(call(classOf[SelectNotNullColFunction],$"a",$"b") as "a")
    //      .toAppendStream[Row]
    //      .print()

    //    tb1.unionAll(tb1.addOrReplaceColumns(lit("all") as "a"))
    //      .toAppendStream[Row]
    //      .print("______________>")

    tb1
//      .addColumns($"a".cast(DataTypes.BIGINT()) as "a_temp")
      .addColumns($"a".cast(DataTypes.DOUBLE()) as "a_temp1")
//      .addOrReplaceColumns(call(classOf[ChangeNullToZero], $"a_temp") as "a")
      .toAppendStream[Row]
      .print()

    env.execute()
  }
}
