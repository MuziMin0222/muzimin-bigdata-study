package com.lhm.graphx

import org.apache.spark.graphx.{Edge, Graph, VertexId, VertexRDD}
import org.apache.spark.rdd.RDD

/**
 * @author : 李煌民
 * @date : 2021-08-05 18:33
 *       ${description}
 **/
object GraphxDemo2 {
  def main(args: Array[String]): Unit = {
    val value_1 = "18779700731".hashCode.toLong
    val value_7 = "13767538897".hashCode.toLong
    val value_2 = "1367930183".hashCode.toLong
    val value_3 = "device_id_1".hashCode.toLong
    val value_4 = "ck1".hashCode.toLong
    val value_5 = "aaa".hashCode.toLong
    val value_6 = "bbb".hashCode.toLong

    val verticesArr = Array(
      (value_1, ("18779700731", "mobile_number")),
      (value_7, ("13767538897", "mobile_number")),
      (value_2, ("1367930183@qq.com", "email")),
      (value_3, ("device_id_1", "device_id")),
      (value_4, ("ck1", "cookie")),
      (value_5, ("aaa", "device_id")),
      (value_6, ("bbb", "cookie"))
    )

    val edgeArr = Array(
      Edge(value_1, value_2, ""),
      Edge(value_7, value_2, ""),
      Edge(value_1, value_3, ""),
      Edge(value_2, value_4, ""),
      Edge(value_6, 1L, "")
    )

    //构造点RDD
    val verticesRDD: RDD[(VertexId, (String, String))] = sc.parallelize(verticesArr)
    //构造边RDD
    val edgeRDD: RDD[Edge[String]] = sc.parallelize(edgeArr)

    //连通图
    val graph = Graph(verticesRDD, edgeRDD)

    //最大联通图
    val maxGraph = graph.connectedComponents()

    val vertices: VertexRDD[VertexId] = maxGraph.vertices
    val joinRDD: RDD[(VertexId, (VertexId, (String, String)))] = vertices.join(verticesRDD)

    joinRDD.map(
      item => {
        item._2
      }
    ).groupByKey()
      .foreach(println(_))

  }
}
