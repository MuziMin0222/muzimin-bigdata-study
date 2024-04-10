package itcast.Stream.source

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-23 17:34
 *       演示自定义非并行数据源实现
 **/
object MyRichSourceParalle {
  def main(args: Array[String]): Unit = {
    val senv = StreamExecutionEnvironment.getExecutionEnvironment

    //添加自定义的数据源
    //setParallelism如果设置的个数大于1  会出错，因为该数据源不是并行的
    val sourceDS = senv.addSource(new MyRichParalleSourceFunction).setParallelism(2)
    //打印数据
    sourceDS.print()
    senv.execute()
  }
}

//这里的泛型是我们自定义source的返回值类型
class MyRichParalleSourceFunction extends RichParallelSourceFunction[Int] {
  //todo 创建一些连接等操作，只执行一次
  override def open(parameters: Configuration): Unit = super.open(parameters)

  //todo 关闭连接等操作
  override def close(): Unit = super.close()

  //发送数据，生产数据的方法
  var count: Int = 0
  var flag = true

  override def run(sourceContext: SourceFunction.SourceContext[Int]): Unit = {
    while (flag) {
      count += 1
      //通过上下文对象发送数据
      sourceContext.collect(count)
      Thread.sleep(1000)
    }
  }

  //取消方法，取消是通过控制一个变量来影响run方法中的while循环
  override def cancel(): Unit = {
    flag = false
  }
}
