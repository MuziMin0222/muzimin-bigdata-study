package itcast.batch

import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
 * @author : 李煌民
 * @date : 2020-09-28 15:44
 **/
object DataSource {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    //基于集合
    val eleDS = env.fromElements("spark", "java", "hadoop", "flink")
    eleDS.print()

    env.fromCollection(Array("spark", "java", "hadoop", "flink")).print()

    env.generateSequence(1, 9).print()

    env.readTextFile("D:\\a.txt").print()
    env.readTextFile("hdfs://hadoop01:9000/a.txt")
    //读取CSV文件需要准备一个样例类
    case class aaa(id: Int, name: String)
    env.readCsvFile[aaa]("D:\\a.csv").print()
    env.readTextFile("D:\\a.txt.gz").print()
    val conf = new Configuration()
    conf.setBoolean("recursive.flie.enumeration", true)
    env.readTextFile("D:\\a_dir\\").withParameters(conf)

  }
}
