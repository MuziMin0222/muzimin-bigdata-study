package itcast.SQL

import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2020-12-10 10:22
 *       ${description}
 **/
object Example {
  def main(args: Array[String]): Unit = {
    val table = ds.toTable(tenv, $"user", $"product", $"amount")

    val resTable = tenv.sqlQuery(
      s"""
         |select
         |sum(amount) from $table group by user
         |""".stripMargin)
    resTable.toRetractStream[Row].print("resTable")

    tenv.createTemporaryView("Orders", ds, $"user", $"product", $"amount")
    // run a SQL query on the Table and retrieve the result as a new Table
    val result2 = tenv.sqlQuery(
      "SELECT product, amount FROM Orders")
    result2.toRetractStream[Row].print("result2")

    env.execute()
  }
}
