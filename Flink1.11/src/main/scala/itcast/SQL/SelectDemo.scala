package itcast.SQL

import java.time.Duration

import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.api._
import org.apache.flink.types.Row
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-10 10:43
 *       ${description}
 **/
object SelectDemo {
  def main(args: Array[String]): Unit = {
    // enable checkpointing
    tenv.getConfig.getConfiguration.set(
      ExecutionCheckpointingOptions.CHECKPOINTING_MODE, CheckpointingMode.EXACTLY_ONCE)
    tenv.getConfig.getConfiguration.set(
      ExecutionCheckpointingOptions.CHECKPOINTING_INTERVAL, Duration.ofSeconds(10))

    val table = ds.toTable(tenv, $"user", $"product", $"amount")
    val tableRes1 = tenv.sqlQuery(
      s"""
         |select * from $table
         |""".stripMargin)

    tenv.createTemporaryView("Orders", ds, $"user", $"product", $"amount", $"proctime".proctime)

    val tableRes2 = tenv.sqlQuery(
      s"""
         |SELECT user, SUM(amount) FROM Orders GROUP BY TUMBLE(proctime, INTERVAL '5' SECOND), user
         |""".stripMargin)
    tableRes2.toRetractStream[Row].print()

    env.execute()
  }
}
