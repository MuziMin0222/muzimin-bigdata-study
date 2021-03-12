package com.MuziMin.source_02

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @author : 李煌民
 * @date : 2021-02-25 13:42
 *       ${description}
 **/
object FileSourceDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC)

    val textSourceDS = env.readTextFile("D:\\code\\workspace_IdeaUi\\RoadToBigData\\Flink1.12\\src\\main\\resources\\data\\world.txt")
    val dirSourceDS = env.readTextFile("D:\\code\\workspace_IdeaUi\\RoadToBigData\\Flink1.12\\src\\main\\resources\\data\\dir")
    val zipSourceDS = env.readTextFile("D:\\code\\workspace_IdeaUi\\RoadToBigData\\Flink1.12\\src\\main\\resources\\data\\zip.tar")

    textSourceDS.print("txt")
    dirSourceDS.print("dir")
    zipSourceDS.print("zip")

    env.execute()
  }
}
