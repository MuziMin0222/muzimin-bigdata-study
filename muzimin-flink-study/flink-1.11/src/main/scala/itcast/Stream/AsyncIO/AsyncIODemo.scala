package itcast.Stream.AsyncIO

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util
import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.scala.{AsyncDataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.concurrent.Executors
import org.apache.flink.streaming.api.scala.async.{ResultFuture, RichAsyncFunction}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

/**
 * @author : 李煌民
 * @date : 2020-11-20 17:12
 *       读取Mysql数据
 **/
object AsyncIODemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //加载集合中的ID数据
    val cityId = env.fromCollection(List(1, 2, 3, 4, 5, 6, 7, 8, 9)).map(_.toString)

    //使用异步IO
    val resDS = AsyncDataStream.unorderedWait(
      cityId,
      new MyAsyncFunction,
      1,
      TimeUnit.SECONDS
    )
    resDS.print()

    env.execute()
  }
}

/*使用异步IO
1、准备一个类实现AsyncRichFunction
    open创建或打开数据链接，一般使用连接池获取链接
    重写timeout方法，处理异步请求超时的问题
    发送异步请求方法，asyncinvoke
2、使用AsyncDataStream工具类，调用orderedWait等方法实现异步操作流

  * @tparam IN The type of the input value.
  * @tparam OUT The type of the output value.
 */
class MyAsyncFunction extends RichAsyncFunction[String, String] {
  var conn: Connection = _
  var ps: PreparedStatement = _
  var map: util.HashMap[String, String] = _

  //创建Mysql链接
  override def open(parameters: Configuration): Unit = {
    conn = DriverManager.getConnection("jdbc:mysql://hadoop01:3306/flink", "root", "123456")
    ps = conn.prepareStatement("select * from cityMapping")
    map = new util.HashMap[String, String]()
  }

  //关闭链接
  override def close(): Unit = {
    if (conn != null) conn.close()
    if (ps != null) conn.close()
  }

  //处理
  override def timeout(input: String, resultFuture: ResultFuture[String]): Unit = {
    println("input >>" + input + "----------请求超时----------------")
  }

  //定义一个异步回调的上下文
  implicit lazy val executor: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.directExecutor())

  //异步请求方法：input：输入数据，resultFuture：异步请求结果对象
  override def asyncInvoke(input: String, resultFuture: ResultFuture[String]): Unit = {
    val rs = ps.executeQuery()
    while (rs.next()) {
      val cityId = rs.getInt("cityId").toString
      val cityName = rs.getString("cityName")
      map.put(cityId, cityName)
    }
    val value = map.get(input)
    //执行异步请求
    Future {
      resultFuture.complete(Array(input + ">>>>>" + value))
    }
  }
}

