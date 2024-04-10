package itcast.Stream.State

import java.time.Duration

import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.state.{MapState, MapStateDescriptor}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2020-12-17 10:59
 *       ${description}
 **/
object UseMapStateToDistinct {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    val sourceDS = env.addSource(new MySource())
      .assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(5))
        .withTimestampAssigner(new SerializableTimestampAssigner[String] {
          override def extractTimestamp(element: String, recordTimestamp: Long): Long = {
            System.currentTimeMillis()
          }
        })
      )
      .keyBy(str => str)
      .process(new KeyedProcessFunction[String, String, String] {
        //声明MapState
        var mapState: MapState[String, Int] = _
        var mapStateDesc: MapStateDescriptor[String, Int] = _

        override def open(parameters: Configuration): Unit = {
          //使用TypeInformation来包装Key Value类型
          mapStateDesc = new MapStateDescriptor[String, Int]("mapState", TypeInformation.of(classOf[String]), TypeInformation.of(classOf[Int]))
          mapState = getRuntimeContext.getMapState(mapStateDesc)
        }

        override def processElement(value: String, ctx: KeyedProcessFunction[String, String, String]#Context, out: Collector[String]): Unit = {
          mapState.get(value) match {
            case 1 => {
              //表示已经存在于State中
            }
            case _ => {
              mapState.put(value, 1)
            }
          }
          out.collect(mapState.keys().iterator().next())
        }
      })

    sourceDS.print("去重后--->")
    env.execute()
  }

}
