package itcast.Stream.source

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
 * @author : 李煌民
 * @date : 2020-10-26 10:48
 *       ${description}
 *       测试Mysql自定义source
 **/
object MysqlSourceDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.addSource(new RichParallelSourceFunction[Student] {
      var conn: Connection = null
      var ps: PreparedStatement = null
      var isRun = true

      override def open(parameters: Configuration): Unit = {
        conn = DriverManager.getConnection("jdbc:mysql://123.56.25.184:5729/db_join", "root", "root")
        ps = conn.prepareStatement("select * from tb_a")
      }

      override def close(): Unit = {
        if (conn != null) {
          conn.close()
        }
        if (ps != null) {
          ps.close()
        }
      }

      override def run(sourceContext: SourceFunction.SourceContext[Student]): Unit = {
        val res = ps.executeQuery()
        while (isRun) {
          while (res.next()) {
            val id = res.getString("id")
            val name = res.getString("name")
            val stu = Student(id, name)
            sourceContext.collect(stu)
          }
        }
        TimeUnit.SECONDS.sleep(5)
      }

      override def cancel(): Unit = {
        isRun = false
      }
    }).setParallelism(1)
    sourceDS.print()
    env.execute()
  }
}

case class Student(id: String, name: String)
