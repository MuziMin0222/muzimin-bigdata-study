package itcast.batch.operation

import java.util

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
 * @author : 李煌民
 * @date : 2020-10-21 15:45
 *       ${description}
 **/
object BroadcastDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val scoreDS = env.fromCollection(List((1, "语文", 50), (2, "英语", 51), (3, "数学", 52), (1, "大数据", 53)))
    //广播学生数据集
    val studentDS = env.fromCollection(List((1, "张三"), (2, "李四"), (3, "王五")))

    val resDS = scoreDS.map(
      //in 就是输入的数据类型，out是输出的数据类型
      new RichMapFunction[(Int, String, Int), (String, String, Int)] {
        //广播变量的值放在成员变量位置
        var StuMap: Map[Int, String] = null

        //只执行一次，通常用来获取广播变量的值
        override def open(parameters: Configuration): Unit = {
          val StuList: util.List[(Int, String)] = getRuntimeContext.getBroadcastVariable[(Int, String)]("student")
          //list类型数据转为scala的map类型
          import scala.collection.JavaConverters._
          StuMap = StuList.asScala.toMap[Int, String]
        }

        //每条数据执行一次map方法，获取广播变量的方法不适合在此处执行，在Open方法中进行
        override def map(in: (Int, String, Int)): (String, String, Int) = {
          val stuId = in._1
          val stuName = StuMap.getOrElse(stuId, null)
          (stuName, in._2, in._3)
        }
      }
      //广播学生数据
    ).withBroadcastSet(studentDS, "student")

    resDS.print()
  }
}
