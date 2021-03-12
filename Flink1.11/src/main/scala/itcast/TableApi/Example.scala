package itcast.TableApi

import org.apache.flink.streaming.api.functions.source._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-07 17:18
 *       ${description}
 **/
object Example {
  def main(args: Array[String]): Unit = {
    val bsEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv: StreamTableEnvironment = StreamTableEnvironment.create(bsEnv, bsSettings)

    //或者这样创建环境变量
    //    val tenv2: StreamTableEnvironment = StreamTableEnvironment.create(bsEnv)

    val stream: DataStream[Order] = bsEnv.addSource(
      new RichSourceFunction[Order] {
        var flag = true

        override def run(ctx: SourceFunction.SourceContext[Order]): Unit = {
          var count = 0
          while (flag) {
            ctx.collect(Order(count, s"name_$count", s"age_$count"))
            count = count + 1
            Thread.sleep(1000)
          }
        }

        override def cancel(): Unit = {
          flag = false
        }
      }
    )
    val dataTable: Table = tenv.fromDataStream[Order](stream)

    val resTable = dataTable
      .select($"name", $"age")
      .filter($"name" === "name_2")

    //直接用sql实现
    tenv.createTemporaryView("data_table",dataTable)

    val sql =
      """
        |select id,name,age from data_table where id = 2
        |""".stripMargin
    val resSqlTab = tenv.sqlQuery(sql)

    resTable.toAppendStream[(String,String)].print()

    resSqlTab.toAppendStream[Order].print()

    bsEnv.execute()
  }
}
