package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 16:25
 *       ${description}
 **/
object ReduceGroupDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List(("java", 1), ("spark", 1), ("scala", 1), ("java", 2)))
    sourceDS
      .groupBy(item => {
        item._1
      })
      .reduceGroup(
        iter => {   //将每一个组中的数据放入在迭代器中
          iter.reduce(
            (t1, t2) => { //再对迭代器进行聚合操作
              (t1._1, t1._2 + t2._2)
            }
          )
        }
      ).print()

    sourceDS
      .groupBy(0)
      .reduceGroup(
        iter => {
          iter.reduce(
            (t1, t2) => {
              (t1._1, t1._2 + t2._2)
            }
          )
        }
      ).print()
  }
}
