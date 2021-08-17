package com.lhm.graphx

import org.apache.spark.graphx.{Edge, Graph, VertexId, VertexRDD}
import org.apache.spark.rdd.RDD

/**
 * @author : 李煌民
 * @date : 2021-08-05 14:22
 *       ${description}
 **/
object GraphxDemo1 {
  def main(args: Array[String]): Unit = {
    //创建顶点RDD集合 (顶点，（属性，属性）)
    val usersRDD: RDD[(Long, (String, String))] = sc.parallelize(Seq(
      (3L, ("rxin", "student")),
      (7L, ("jgonzal", "postdoc")),
      (5L, ("franklin", "prof")),
      (2L, ("istoica", "prof"))))

    //创建边RDD集合
    val relationshipsRDD: RDD[Edge[String]] = sc.parallelize(Seq(
      Edge(3L, 7L, "collab"),
      Edge(5L, 3L, "advisor"),
      //      Edge(2L, 5L, "colleague"),
      Edge(5L, 7L, "pi")))

    val defaultUser = ("john doe", "missing")

    //建立一个图
    val graph: Graph[(String, String), String] = Graph(usersRDD, relationshipsRDD, defaultUser)

    //将顶点取出 进行查看，以最小值作为唯一顶点，进行联通
    val vertices: VertexRDD[VertexId] = graph
      .connectedComponents()
      .vertices
    vertices.foreach(println(_))

    //拿到顶点对应的所有值
    val joinRDD: RDD[(VertexId, (VertexId, (String, String)))] = vertices.join(usersRDD)
    joinRDD
      .map {
        case (id, (vid, (str1, str2))) => {
          (vid, List((id, str1, str2)))
        }
      }
      .reduceByKey(_ ++ _)
      .foreach(println(_))

    //计算顶点为postdoc的个数
    val countRes = graph.vertices.filter {
      case (id, (name, pos)) => {
        pos == "postdoc"
      }
    }.count()
    println(countRes)

    //计算边中起始点 > 结束点的count值
    val countEdgeRes = graph.edges.filter(
      e => {
        e.srcId > e.dstId
      }
    )
    val countEdgeRes_1 = graph.edges.filter {
      case Edge(src, dst, prop) => {
        src > dst
      }
    }.count
    println(countEdgeRes)
  }
}
