package com.lhm.graphx.page_rank

import com.lhm.graphx.page_rank.pregel.SocialGraph
import org.apache.spark.graphx.VertexRDD
import org.apache.spark.sql.SparkSession

/**
 * @author: 李煌民
 * @date: 2022-08-31 10:13
 *        ${description}
 **/
object PageRankJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getSimpleName)
      .master("local[*]")
      .getOrCreate()

    val socialGraph: SocialGraph = new SocialGraph(spark)

    println("-----------输出点集合----------------------")
    socialGraph.MyVerts.take(20).foreach(println(_))

    println("-----------输出边集合----------------------")
    socialGraph.MyEdges.take(20).foreach(println(_))

    val TOLERANCE: Double = 0.0001

    import scala.compat.Platform.{EOL => D}
    val dynamicPageRankVerts = ranks(socialGraph, TOLERANCE)

    println("-------输出动态PageRank的点集合-----------")
    dynamicPageRankVerts.take(20).foreach(println(_))
    val topUsersDynamically = handleResult(socialGraph, dynamicPageRankVerts).mkString(D)

    val staticPageRankVerts = static(socialGraph, TOLERANCE)

    println("-------输出静态PageRank的点集合-----------")
    staticPageRankVerts.take(20).foreach(println(_))
    val topUsersIterative = handleResult(socialGraph, staticPageRankVerts).mkString(D)

    println(s"---------排名前10的用户用TOLERANCE计算，直到收敛到0.0001------------ $D $topUsersDynamically")
    println(s"---------迭代20次统计网络中排名前10的用户------------------ $D $topUsersIterative")

    spark.close()
  }

  def handleResult(socialGraph: SocialGraph, ranks: VertexRDD[Double]): Array[(String, Double)] = {
    socialGraph.MyVerts.join(ranks).map {
      case (_, (username, rank)) => (username, rank)
    }.sortBy({ case (_, rank) => rank }, ascending = false).take(10)
  }

  /**
   * GraphX附带了PageRank的静态和动态实现，作为PageRank对象上的方法。
   * 动态 PageRank 则运行直到排名收敛（即，停止更改超过指定的容差）
   *
   * @param socialGraph
   * @param tolerance
   * @return
   */
  def ranks(socialGraph: SocialGraph, tolerance: Double): VertexRDD[Double] =
    socialGraph.graph.pageRank(tol = tolerance).vertices

  /**
   * 静态 PageRank 运行固定次数的迭代
   *
   * @param socialGraph
   * @param tolerance
   * @return
   */
  def static(socialGraph: SocialGraph, tolerance: Double): VertexRDD[Double] =
    socialGraph.graph.staticPageRank(numIter = 20).vertices
}
