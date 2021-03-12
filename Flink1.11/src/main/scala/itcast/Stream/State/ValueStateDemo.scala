package itcast.Stream.State

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import itcast.Stream._
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
 * @author : 李煌民
 * @date : 2020-11-17 14:03
 *       ${description}
 **/
object ValueStateDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.addSource(kafkaConsumer)

    val mapDS = sourceDS.map(
      item => {
        val itemArr = item.split(",")
        (itemArr(0), itemArr(1).trim.toInt)
      }
    )

    val keyStream = mapDS.keyBy(_._1)

    //keyStream.maxBy(1)   //现在不用这个方法，使用state来得到每一个key对应的最大值
    val maxMapDS = keyStream.map(
      new RichMapFunction[(String, Int), (String, Int)] {
        //声明一个valueState(不是创建),valueState无需关心key是谁以及key value之间的映射，Flink自己维护
        var maxValueState: ValueState[Int] = _

        //通过上下文才能获取真的state，上下文这种操作在执行一次的方法中使用并获取真正的状态对象
        override def open(parameters: Configuration): Unit = {
          //定义一个state描述器，参数：state的名称，数据类型的字节码文件
          val maxValueDesc = new ValueStateDescriptor[Int]("maxValue", classOf[Int])
          //根据上下文基于描述器获取state
          maxValueState = getRuntimeContext.getState(maxValueDesc)
        }

        override def map(in: (String, Int)): (String, Int) = {
          //in是一条新数据，需要与原来的的最大值valueState进行比较判断
          //获取valueState中的数据
          val maxNumInState = maxValueState.value()
          if (in._2 > maxNumInState) {
            maxValueState.update(in._2)
          }
          //返回最大值
          (in._1, maxValueState.value())
        }
      }
    )
    maxMapDS.print()
    env.execute()
  }
}
