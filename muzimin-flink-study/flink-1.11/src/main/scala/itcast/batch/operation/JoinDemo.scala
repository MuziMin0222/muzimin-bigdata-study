package itcast.batch.operation

import itcast.batch.bean.{Score, project}
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-10-19 17:16
 *       ${description}
 **/
object JoinDemo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val scoreDS = env.readCsvFile[Score]("D:\\score.csv")
    val projectDS = env.readCsvFile[project]("D:\\project.csv")
    scoreDS.join(projectDS).where(0).equalTo(0).map(item => {
      (item._1.name, item._1.score, item._2.project)
    }).print()
    println("---------")
    scoreDS.join(projectDS).where("id").equalTo("id").print()

    println("--------------")

    val leftJoinDS = scoreDS.leftOuterJoin(projectDS).where(0).equalTo(0)
    //要使用Apply方法进行解析
    val applyDS = leftJoinDS.apply(
      (left, right) => {
        if (right == null) {
          (left.name, left.score, "null")
        } else {
          (left.name, left.score, right.project)
        }
      }
    )
    applyDS.print()

  }
}
