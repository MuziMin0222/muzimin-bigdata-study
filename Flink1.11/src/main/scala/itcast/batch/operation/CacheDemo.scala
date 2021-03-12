package itcast.batch.operation

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

import scala.io.Source

/**
 * @author : 李煌民
 * @date : 2020-10-21 18:10
 *       ${description}
 **/
object CacheDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List((1, "java"), (2, "scala"), (3, "flink")))
    //把一个文件数据注册为分布式缓存文件
    env.registerCachedFile("hdfs://hadoop01:9000/student.txt", "cache_student")

    val resDS = sourceDS.map(
      new RichMapFunction[(Int, String), (String, String)] {
        var stuMap: Map[Int, String] = null

        //通过上下文获取缓存文件
        override def open(parameters: Configuration): Unit = {
          val file = getRuntimeContext.getDistributedCache.getFile("cache_student")
          //对该File对象进行解析
          val tuples = Source.fromFile(file).getLines().map(
            line => {
              val arr = line.split(",")
              (arr(0).toInt, arr(1)) // id,name
            }
          )
          //对分布式缓存文件进行转map处理
          stuMap = tuples.toMap
        }

        override def map(in: (Int, String)): (String, String) = {
          val stuId = in._1
          val stuName = stuMap.getOrElse(stuId, null)
          (stuName, in._2)
        }
      }
    )
    resDS.print()
  }
}
