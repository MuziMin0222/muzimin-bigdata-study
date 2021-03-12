package itcast.TableApi

import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2020-12-31 16:43
 *       ${description}
 **/
object WindowOverDemo {
  def main(args: Array[String]): Unit = {
    //类似于SQL OVER子句。基于前一行和后一行的窗口（范围），为每一行计算窗口聚合
    //preceding 为over窗口设置前面的偏移(基于时间或行计数间隔)。UNBOUNDED_RANGE 无边界的范围
    //following 为over窗口设置以下偏移量(基于时间或行计数间隔)。当前范围
    tb1.window(Over partitionBy $"a" orderBy $"rt" preceding UNBOUNDED_RANGE following CURRENT_RANGE as "w")
      .select($"a", $"b", $"b".cast(DataTypes.INT()).avg() over $"w", $"b".cast(DataTypes.INT()).max().over($"w"), $"b".cast(DataTypes.INT()).min().over($"w"))
      .toRetractStream[Row]
      .print("over window")

    env.execute()
  }
}
