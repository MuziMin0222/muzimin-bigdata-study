package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 11:09
 *       ${description}
 **/
object ReduceDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List(
      ("java", 1),
      ("spark", 1),
      ("java", 1)
    ))
    //按索引进行分组，0是第一个,
    sourceDS.groupBy(0).sum(1).print()
    //自己reduce聚合
    sourceDS.groupBy(item => {
      item._1
    }).reduce((tuple1, tuple2) => {
      (tuple1._1, tuple1._2 + tuple2._2)
    }).print()

    /*
    (java,2)
    (spark,1)
    */
  }
}
