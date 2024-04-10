package com.MuziMin.table_sql_10

import com.MuziMin.table_sql_10.bean.Wc
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2021-03-01 10:42
 *       ${description}
 **/
object WorldCountDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv = StreamTableEnvironment.create(env, settings)

    val wordsDS = env.fromElements(
      Wc("Hello", 1),
      Wc("World", 1),
      Wc("Hello", 1))

    val t_words = tenv.fromDataStream(wordsDS, $"name", $"num")

    val sql =
      s"""
         |select name,sum(num) from $t_words group by name
         |""".stripMargin

    val resTable = tenv.sqlQuery(sql)

    val resDS = tenv.toRetractStream[Wc](resTable)
    resDS.print()

    //方式二
    t_words
      .groupBy($"name")
      .select($"name", $"num".sum.as("num"))
      .filter($"num" >= 2)
      .toRetractStream[Wc]
      .print("table:")

    env.execute()
  }
}
