package itcast.Stream.State

import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.runtime.state.{FunctionInitializationContext, FunctionSnapshotContext}
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-11-18 11:34
 *       ${description}
 **/
object listStateDemo {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //设置环境参数
    env.enableCheckpointing(1000)
    env.setStateBackend(new FsStateBackend("file:///D:/ck"))
    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(2)
    //固定延迟重启策略，程序出现异常的时候，重启三次，每次延迟五秒钟后重启，超过三次，程序退出
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 5000))
    //添加自定义source
    val sourceDS = env.addSource(new MySource)

    sourceDS.print()
    env.execute()
  }
}

//继承sourceFunction同时要实现CheckpointedFunction（operatorState需要实现此接口）
class MySource extends SourceFunction[String] with CheckpointedFunction {
  var flag = true
  var offset = 0L
  var listState: ListState[Long] = _

  //生成数据
  override def run(sourceContext: SourceFunction.SourceContext[String]): Unit = {
    while (flag) {
      //需要从listState中获取之前的offset
      val listStateIter = listState.get().iterator()
      if (listStateIter.hasNext) offset = listStateIter.next()
      //发送一个递增的数据，每次增加1
      offset += 1
      sourceContext.collect(offset.toString)
      //设置休眠
      TimeUnit.SECONDS.sleep(1)
      //设置异常，让程序出现异常之后重启，看看是否会从ListState中恢复
      if (offset % 2 == 0) {
        println("program exception.....")
        throw new RuntimeException("偏移量为2的倍数，出现异常。")
      }
    }
  }

  //取消
  override def cancel(): Unit = {
    flag = false
  }

  //初始化state的方法
  override def initializeState(functionInitializationContext: FunctionInitializationContext): Unit = {
    //定义一个ListState的描述器，存储的就是offset值，是long类型
    val listStateDesc = new ListStateDescriptor[Long]("listState", classOf[Long])
    //获取ListState
    listState = functionInitializationContext.getOperatorStateStore.getListState(listStateDesc)
  }

  //进行checkPoint时制作state快照(把state持久化存储)会调用这个方法
  override def snapshotState(functionSnapshotContext: FunctionSnapshotContext): Unit = {
    //把offsetState清空
    listState.clear()
    //把最新的offset加入state中
    listState.add(offset)
  }
}
