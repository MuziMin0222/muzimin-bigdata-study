package com.MuziMin.checkpoint_09

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.scala._

/**
 * @author : 李煌民
 * @date : 2021-03-01 10:07
 *       ${description}
 **/
object CkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    //设置Checkpoint的时间间隔为1000ms做一次Checkpoint/其实就是每隔1000ms发一次Barrier!
    env.enableCheckpointing(1000)
    //设置State状态存储介质/状态后端
    env.setStateBackend(new FsStateBackend("hdfs://hadoop01:9000/flink-checkpoint"))

    //设置两个Checkpoint 之间最少等待时间,如设置Checkpoint之间最少是要等 500ms(为了避免每隔1000ms做一次Checkpoint的时候,前一次太慢和后一次重叠到一起去了)
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500) //默认是0
    //默认值为0，表示不容忍任何检查点失败
    env.getCheckpointConfig.setTolerableCheckpointFailureNumber(10)
    //设置是否清理检查点,表示 Cancel 时是否需要保留当前的 Checkpoint，默认 Checkpoint会在作业被Cancel时被删除
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)

    //设置checkpoint的执行模式为EXACTLY_ONCE(默认)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    //设置checkpoint的超时时间,如果 Checkpoint在 60s内尚未完成说明该次Checkpoint失败,则丢弃。
    env.getCheckpointConfig.setCheckpointTimeout(60000); //默认10分钟
    //设置同一时间有多少个checkpoint可以同时执行,和至少等待时间冲突，两者设置其一即可
    //env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)//默认为1

    val sourceDS = env.socketTextStream("localhost",9999)

    sourceDS.print()

    env.execute()
  }
}
