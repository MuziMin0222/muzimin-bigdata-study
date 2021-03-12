package itcast.batch

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem

/**
 * @author : 李煌民
 * @date : 2020-09-27 11:08
 *       1、获取一个execution environment
 *       2、加载/创建初始化数据
 *       3、指定这些数据的转换
 *       4、指定将计算结果放在哪里
 *       5、触发程序的执行
 **/
object WordCountDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val sourceDS: DataSet[String] = env.fromElements("fafdsafdafds fdafsdag fdafdsa fdsagbsaf fadghgafdsa fafdsafdsa")

    val flatDF: DataSet[String] = sourceDS.flatMap(_.split(" "))

    val mapDS: DataSet[(String, Int)] = flatDF.map((_, 1))

    //这里使用索引来对哪一个字段进行分组，从0开始
    val groupDS: GroupedDataSet[(String, Int)] = mapDS.groupBy(0)

    //这里使用索引来对哪一个字段进行分组，从0开始
    val sumDS: AggregateDataSet[(String, Int)] = groupDS.sum(1)

    sumDS.print()
    sumDS.writeAsText("hdfs://hadoop01:9000/output_wordCount", FileSystem.WriteMode.OVERWRITE)

    //触发程序的执行
    env.execute(this.getClass.getSimpleName)
  }
}
