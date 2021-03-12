package atguigu.flink_core

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}

object WorldCount {
  def main(args: Array[String]): Unit = {
    //从外部命令中获取参数
    val tool = ParameterTool.fromArgs(args)
    val in = tool.get("in")
    val out = tool.get("out")

    // 1 、构建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

//    val ds: DataSet[String] = env.readTextFile("hdfs://bd1:9000/data/test/wordcount/file_1")
    val ds: DataSet[String] = env.readTextFile(in)

    //3、导入隐式转换
    import org.apache.flink.api.scala._

    //groupBy()：里面参数传入索引值，按元组中的那个元组进行分组操作
    //sun():里面传入元组的索引值，按元组中的哪一个元素进行相加操作
    val mapDS = ds.flatMap(_.split(" ")).map((_,1)).groupBy(0).sum(1)

    //每一个算子都可以设置并行度：setParallelism
//    mapDS.writeAsText("hdfs://bd1:9000/flink_res/res2.txt").setParallelism(1)
    mapDS.writeAsText(out).setParallelism(1)

    env.execute()
  }
}
