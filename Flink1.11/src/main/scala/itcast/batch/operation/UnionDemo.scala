package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-21 11:22
 *       ${description}
 **/
object UnionDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val ds1 = env.fromCollection(List("java","scala","hadoop"))
    val ds2 = env.fromCollection(List("java","hive","flink"))
    ds1.union(ds2).print()
  }

}
