package itcast.TableApi

import org.apache.flink.api.scala._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:41
 *       ${description}
 **/
object sqlQueryDemo {
  def main(args: Array[String]): Unit = {
    tenv.sqlQuery(
      s"""
         |select * from $tb1
         |""".stripMargin)
      .toAppendStream[Row].print("sql--->")

    env.execute()
  }
}
