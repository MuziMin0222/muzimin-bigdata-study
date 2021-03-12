package itcast.batch.operation

import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 10:35
 *       ${description}
 **/
object FlatMapDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List(
      "张三,中国,江西,赣州",
      "李四,日本,东京,憨憨"
    ))

    //flatMap 先对元素进行Map操作，改变结构，得到一个集合，对集合内的元素进行展开操作
    val flatMapDS = sourceDS.flatMap(
      item => {
        val arr = item.split(",")
        val list = List(
          (arr(0), arr(1)),
          (arr(0), arr(1), arr(2)),
          (arr(0), arr(1), arr(2), arr(3))
        )
        list
      }
    )
    flatMapDS.print()
    /*
    (张三,中国)
    (张三,中国,江西)
    (张三,中国,江西,赣州)
    (李四,日本)
    (李四,日本,东京)
    (李四,日本,东京,憨憨)
     */
  }
}
