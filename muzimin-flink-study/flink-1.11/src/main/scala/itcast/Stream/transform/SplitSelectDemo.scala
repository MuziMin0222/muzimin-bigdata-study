package itcast.Stream.transform

import java.{lang, util}

import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @author : 李煌民
 * @date : 2020-11-06 12:00
 *       ${description}
 **/
object SplitSelectDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.generateSequence(1,10)
    val splitDS = sourceDS.split(
      number => {
        number % 2 match {
          case 0 => List("s")
          case 1 => List("d")
        }
      }
    )

    val split1 = sourceDS.split(
      new OutputSelector[Long]() {
        override def select(out: Long): lang.Iterable[String] = {
          val list = new util.ArrayList[String]()
          out % 2 match {
            case 0 => list.add("s")
            case 1 => list.add("d")
          }
          list
        }
      }
    )
    split1.select("s").print("这是双数：")
//    splitDS.select("s").print()    //将双数输出
//    splitDS.select("d").print()    //将单数输出
    splitDS.select("s","d").print()    //全部数据输出
    env.execute()
  }
}
