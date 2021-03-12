package itcast.TableApi.TableFunction

import itcast.TableApi._
import org.apache.flink.table.annotation.{DataTypeHint, FunctionHint}
import org.apache.flink.table.api._
import org.apache.flink.api.scala._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.functions.TableFunction
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2021-01-04 18:20
 *       ${description}
 **/
object Demo2 {
  def main(args: Array[String]): Unit = {

    val res = tb1
      .addColumns(concat($"a", "|,|", $"b") as "demo1")
      .joinLateral(call(classOf[SplitFunction], $"demo1"))
      .select($"word")

    res.toAppendStream[Row]
      .print()

    env.execute()
  }
}

@FunctionHint(output = new DataTypeHint("ROW<word STRING>"))
class SplitFunction extends TableFunction[Row] {
  def eval(str: String): Unit = {
    // use collect(...) to emit a row
    val strArr = str.split(",")
    for (elem <- strArr) {
      collect(Row.of(elem))
    }
  }
}