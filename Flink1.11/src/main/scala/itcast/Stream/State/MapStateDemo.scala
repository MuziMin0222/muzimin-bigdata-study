package itcast.Stream.State

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import itcast.Stream._
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.common.state.{MapState, MapStateDescriptor}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
 * @author : 李煌民
 * @date : 2020-11-18 10:16
 *       ${description}
 **/
object MapStateDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.addSource(kafkaConsumer)

    val keyStream = sourceDS.map(
      item => {
        val itemArr = item.split(",")
        (itemArr(0), itemArr(1).trim.toInt)
      }
    ).keyBy(_._1)

    val mapStateDS = keyStream.map(
      new RichMapFunction[(String, Int), (String, Int)] {
        //声明mapState
        var mapState: MapState[String, Int] = _

        //获取MapState
        override def open(parameters: Configuration): Unit = {
          //使用TypeInformation来包装Key Value类型
          val mapStateDesc = new MapStateDescriptor[String, Int]("mapState", TypeInformation.of(classOf[String]), TypeInformation.of(classOf[Int]))
          mapState = getRuntimeContext.getMapState(mapStateDesc)
        }

        override def map(in: (String, Int)): (String, Int) = {
          //获取新数据的Key
          val key = in._1
          //根据key来获取state中的历史结果，stateValue就是之前累加的结果
          val stateValue = mapState.get(key)
          mapState.put(key, in._2 + stateValue)
          //返回结果
          (key, mapState.get(key))
        }
      }
    )
    mapStateDS.print()
    env.execute()
  }
}
