package itcast.TableApi

import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:42
 *       ${description}
 **/
object sqlQueryWindowDemo {
  def main(args: Array[String]): Unit = {
    tenv.sqlQuery(
      s"""
         |select
         |  a,
         |  count(distinct b)
         |from $tb1
         |group by TUMBLE(rt, INTERVAL '5' SECOND),a
         |""".stripMargin)
      .toRetractStream[Row].print("sql1---->")

    env.execute()
  }
}
