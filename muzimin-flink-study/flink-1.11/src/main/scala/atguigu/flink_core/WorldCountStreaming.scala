package atguigu.flink_core

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object WorldCountStreaming {
  def main(args: Array[String]): Unit = {
    //从外部命令中获取参数
    val tool = ParameterTool.fromArgs(args)
    val host = tool.get("host")
    val port = tool.get("port").toInt

    //创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //接收Socket文本流
    val textDStream = env.socketTextStream(host,port)

    //导入隐式转换
    import org.apache.flink.api.scala._
    val dStream = textDStream.flatMap(_.split(" ")).filter(_.nonEmpty).map((_,1)).keyBy(0).sum(1)

    dStream.print()

    //让程序执行
    env.execute()
  }
}
