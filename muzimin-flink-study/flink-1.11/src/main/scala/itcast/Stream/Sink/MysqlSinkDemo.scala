package itcast.Stream.Sink

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}

/**
 * @author : 李煌民
 * @date : 2020-11-06 13:54
 *       ${description}
 **/
object MysqlSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromElements(tb_a(1000, "lihuangmin_mysql_sink"))
    sourceDS.addSink(
      new RichSinkFunction[tb_a] {
        var ps: PreparedStatement = null
        var conn: Connection = null

        //创建数据库连接
        override def open(parameters: Configuration): Unit = {
          conn = DriverManager.getConnection("jdbc:mysql://123.56.25.184:5729/db_join", "root", "root")
          ps = conn.prepareStatement("insert into tb_a(id,name) values(?,?)")
        }

        //关闭数据库连接
        override def close(): Unit = {
          if (conn != null) {
            conn.close()
          }
          if (ps != null) {
            ps.close()
          }
        }

        //该方法负责写入到Mysql数据库中,value就是上游datastream传入需要写入Mysql的数据
        override def invoke(value: tb_a, context: SinkFunction.Context[_]): Unit = {
          //给占位符进行赋值操作
          ps.setString(2, value.name)
          ps.setInt(1, value.id)
          //执行更新语句
          ps.executeUpdate()
        }
      }
    )

    env.execute()
  }
}

case class tb_a(id: Int, name: String)
