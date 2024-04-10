package com.MuziMin.transformation

import com.MuziMin.source_02.bean.Order
import com.MuziMin.source_02.function.MyOrderSource
import com.MuziMin.transformation_03.function.MyPartitioner
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-02-25 17:52
 *       自定义分区
 **/
object UserDefinedPartitionDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val sourceDS: DataStream[Order] = env.addSource(new MyOrderSource)

    sourceDS.partitionCustom(new MyPartitioner, _.userId).print()

    env.execute()
  }
}
