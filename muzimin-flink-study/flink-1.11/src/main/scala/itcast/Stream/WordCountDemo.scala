package itcast.Stream

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:54
 *       ${description}
 **/
object WordCountDemo {
  def main(args: Array[String]): Unit = {
    //创建一个流处理的运行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //构建socket source数据源
    val sourceDS = env.socketTextStream("localhost", 9999)
    val mapDS = sourceDS.flatMap(_.split(",")).map((_, 1))
    val keyDS = mapDS.keyBy(item => {
      item._1
    })
    //使用窗口进行5秒计算一次
    val windowDS = keyDS.timeWindow(Time.seconds(5))
    val resDS = windowDS.sum(1)

    resDS.print()

    //这里的执行一定要执行
    env.execute()
  }
}
