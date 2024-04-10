package itcast.TableApi

import org.apache.flink.types.Row
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:46
 *       ${description}
 **/
object AsDemo {
  def main(args: Array[String]): Unit = {
    //AS
    tb1.as("temp1", "temp2", "temp3", "temp4").select($"temp1", $"temp4").toAppendStream[Row].print("as")

    env.execute()
  }
}
