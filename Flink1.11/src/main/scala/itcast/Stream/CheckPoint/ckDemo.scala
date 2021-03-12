package itcast.Stream.CheckPoint

import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.time.Time
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.CheckpointingMode

/**
 * @author : 李煌民
 * @date : 2020-11-19 11:48
 *       ${description}
 **/
object ckDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //设置ck属性，开启CK
    //    env.setStateBackend(new FsStateBackend("hdfs://hadoop01:9000/flink/ck1"))
    env.setStateBackend(new FsStateBackend("file:///D://ck"))
    //设置checkpoint的周期间隔，默认是没有开启ck，需要通过指定间隔来开启ck
    env.enableCheckpointing(1000)
    //设置ck的执行语义，最多一次，至少一次，精确一次
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    //设置两次CK之间的最小时间间隔，两次CK之间的时间最少差500ms
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)
    //设置ck的超时时间，如果超过则认为本次ck失败，继续下一次ck即可，现设置60s
    env.getCheckpointConfig.setCheckpointTimeout(60000)
    /*设置CK出现问题，是否让程序报错还是继续任务进行下一次ck，设置为true：让程序报错不往下执行，设置false：不报错进行下次ck
    如果是false就是ck出现问题运行程序继续执行，如果下次ck成功则没有问题，但是如果程序下次ck也没有成功
    此时程序挂掉需要从ck中恢复数据时可能导致程序计算错误，或者时重复计算数据*/
    env.getCheckpointConfig.setFailOnCheckpointingErrors(false)
    //设置任务取消时是否保留检查点，retain：则保存检查点数据，delete：删除ck作业数据
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    //设置程序中允许几个ck任务同时进行
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)

    //设置重启策略
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, Time.of(2, TimeUnit.SECONDS)))

    val sourceDS = env.addSource(
      new RichSourceFunction[(String, Int)] {
        var count: Int = 0
        var falg = true

        override def run(ctx: SourceFunction.SourceContext[(String, Int)]): Unit = {
          while (falg) {
            count += 1
            ctx.collect(("hello flink", count))
            if (count % 2 == 0) {
              throw new RuntimeException("BUG .........")
            }
            TimeUnit.SECONDS.sleep(2)
          }
        }

        override def cancel(): Unit = {
          falg = false
        }
      }
    )
    sourceDS.print()
    env.execute()
  }
}
