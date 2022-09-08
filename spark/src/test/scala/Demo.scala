import com.lhm.graphx.page_rank.pregel.InputDataFlow

/**
 * @author : 李煌民
 * @date : 2021-03-01 12:08
 *       ${description}
 **/
object Demo {
  def main(args: Array[String]): Unit = {

    Seq("1\t\"William\"")
      .flatMap(InputDataFlow.parseNames)
      .foreach(println)
  }
}
