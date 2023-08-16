package itcast.Stream.State

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.concurrent.TimeUnit

import com.alibaba.fastjson.JSON
import itcast.Stream._
import org.apache.flink.api.common.state.{BroadcastState, MapStateDescriptor, ReadOnlyBroadcastState}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.{BroadcastConnectedStream, DataStream, StreamExecutionEnvironment}
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2020-11-18 16:02
 *       ${description}
 **/
object BroadCastStateDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.addSource(kafkaConsumer)

    //拿到Mysql中的数据
    val mysqlDS: DataStream[(String, (String, Int))] = env.addSource(
      new RichSourceFunction[(String, (String, Int))] {
        var conn: Connection = _
        var ps: PreparedStatement = _
        var flag = true

        override def open(parameters: Configuration): Unit = {
          conn = DriverManager.getConnection("jdbc:mysql://hadoop01:3306/flink?useUnicode=true&characterEncoding=UTF-8", "root", "123456")
          ps = conn.prepareStatement("select * from user")
        }

        override def close(): Unit = {
          if (conn != null) conn.close()
          if (ps != null) ps.close()
        }

        override def run(ctx: SourceFunction.SourceContext[(String, (String, Int))]): Unit = {
          val rs = ps.executeQuery()
          while (flag) {
            while (rs.next()) {
              val user_id = rs.getString("user_id")
              val name = rs.getString("name")
              val age = rs.getInt("age")
              //收集器发送数据
              ctx.collect((user_id, (name, age)))
            }
            TimeUnit.SECONDS.sleep(1)
          }
        }

        override def cancel(): Unit = {
          flag = false
        }
      }
    )

    //需要把MysqlSource广播出去，作为broadCastState来使用
    //广播流直接使用broadcastState广播，需要提供一个MapStatedDescriptor
    val mapStateDesc = new MapStateDescriptor[String, (String, Int)]("mapState", TypeInformation.of(classOf[String]), TypeInformation.of(classOf[(String, Int)]))
    val mysqlBroadCastDS = mysqlDS.broadcast(mapStateDesc)

    val processDS = sourceDS.process(
      //泛型： in：输入的数据类型，out：输出的数据类型
      new ProcessFunction[String, (String, String, String, Int)] {
        //自定义业务逻辑，value：一条数据，out：数据收集器，数据处理之后可以使用收集器发送出去
        override def processElement(value: String, ctx: ProcessFunction[String, (String, String, String, Int)]#Context, out: Collector[(String, String, String, Int)]): Unit = {
          val jsonObject = JSON.parseObject(value)
          val userId = jsonObject.getString("userId")
          val eventTime = jsonObject.getString("eventTime")
          val eventType = jsonObject.getString("eventType")
          val productId = jsonObject.getIntValue("productId")

          //发送数据
          out.collect((userId, eventTime, eventType, productId))
        }
      }
    )

    //双流connect,合并流，原来的流依然独立存在
    val connectStream: BroadcastConnectedStream[(String, String, String, Int), (String, (String, Int))] = processDS.connect(mysqlBroadCastDS)

    //使用processFunction来处理connectStream
    //自定义BroadCastProcessFunction实现在处理事件流数据时，获取广播流中的数据，借助state实现
    val resDS = connectStream.process(
      //第一个泛型IN1：事件流中数据类型
      //第二个泛型IN2：广播流中的数据类型
      //第三个泛型OUT：输出的数据类型
      new BroadcastProcessFunction[(String, String, String, Int), (String, (String, Int)), (String, String, String, Int, String, Int)]() {
        //处理事件流的方法   对于广播流的数据是只读的，不能进行修改
        override def processElement(
                                     value: (String, String, String, Int),
                                     ctx: BroadcastProcessFunction[
                                       (String, String, String, Int),
                                       (String, (String, Int)),
                                       (String, String, String, Int, String, Int)]#ReadOnlyContext,
                                     out: Collector[(String, String, String, Int, String, Int)]): Unit = {
          //借助State，需要在processBroadCastElement中把广播流数据存入state，在processElement获取数据
          val broadCastState: ReadOnlyBroadcastState[String, (String, Int)] = ctx.getBroadcastState(mapStateDesc)
          if (broadCastState.contains(value._1)) {
            val tuple = broadCastState.get(value._1)
            out.collect((value._1, value._2, value._3, value._4, tuple._1, tuple._2))
          } else {
            //直接丢弃
          }
        }

        //处理广播流的方法
        override def processBroadcastElement(
                                              value: (String, (String, Int)),
                                              ctx: BroadcastProcessFunction[
                                                (String, String, String, Int),
                                                (String, (String, Int)),
                                                (String, String, String, Int, String, Int)]#Context,
                                              out: Collector[(String, String, String, Int, String, Int)]): Unit = {
          //获取broadcastState
          val broadState: BroadcastState[String, (String, Int)] = ctx.getBroadcastState(mapStateDesc)
          //给state添加新数据
          broadState.put(value._1, value._2)
        }
      }
    )
    resDS.print()
    env.execute()

  }
}
