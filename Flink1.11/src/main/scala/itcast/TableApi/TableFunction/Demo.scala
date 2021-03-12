package itcast.TableApi.TableFunction

import itcast.TableApi._
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.functions.ScalarFunction
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2021-01-04 18:20
 *       ${description}
 **/
object Demo {
  def main(args: Array[String]): Unit = {
    tb1
      .addColumns(call(classOf[SubstringFunction], "myField", 0, 1) as "res")
      .toAppendStream[Row]
      .print()

    env.execute()
  }
}

class SubstringFunction extends ScalarFunction {
  def eval(s: String, begin: Integer, end: Integer): String = {
    println(s)
    println(begin)
    println(end)
    s.substring(begin, end)
  }
}