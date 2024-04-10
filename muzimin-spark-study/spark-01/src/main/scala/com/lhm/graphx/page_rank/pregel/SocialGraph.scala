package com.lhm.graphx.page_rank.pregel

import org.apache.spark.graphx.{Edge, Graph, PartitionID, VertexId, VertexRDD}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author: 李煌民
 * @date: 2022-08-31 11:00
 *        ${description}
 **/
class SocialGraph(spark: SparkSession) {
  type ConnectedUser = (PartitionID, String)
  type DegreeOfSeparation = (Double, String)

  val USER_NAMES = "/Users/muzimin/code/IdeaProjects/RoadToBigData/spark/src/main/resources/UserNames.tsv"
  val USER_GRAPH = "/Users/muzimin/code/IdeaProjects/RoadToBigData/spark/src/main/resources/UserGraph.tsv"

  /**
   * 构建点集合
   *
   * @return
   */
  def MyVerts: RDD[(VertexId, String)] = spark
    .sparkContext
    .textFile("/Users/muzimin/code/IdeaProjects/RoadToBigData/spark/src/main/resources/MyUserGraph.tsv")
    .flatMap(line => {
      line.split(",")
    })
    .filter(!_.equals(""))
    .map(
      item => {
        (item.hashCode.toLong, item)
      }
    )
    .distinct()

  /*def verts: RDD[(VertexId, String)] = spark
    .sparkContext
    .textFile(USER_NAMES)
    .flatMap(line => {
      InputDataFlow.parseNames(line)
    })*/

  /**
   * 构建边集合
   *
   * @return
   */
  /*def edges: RDD[Edge[PartitionID]] = spark
    .sparkContext
    .textFile(USER_GRAPH)
    .flatMap(line => {
      InputDataFlow.makeEdges(line)
    })*/

  def MyEdges: RDD[Edge[String]] = spark
    .sparkContext
    .textFile("/Users/muzimin/code/IdeaProjects/RoadToBigData/spark/src/main/resources/MyUserGraph.tsv")
    .flatMap(line => {
      InputDataFlow.myMakeEdges(line)
    })

  def graph: Graph[String, String] = Graph(MyVerts, MyEdges).cache()

  def getMostConnectedUsers(amount: Int): Array[(VertexId, ConnectedUser)] = {
    graph.degrees.join(MyVerts)
      .sortBy({ case (_, (userName, _)) => userName }, ascending = false)
      .take(amount)
  }

  private def getBFS(root: VertexId): Graph[Double, String] = {
    val initialGraph = graph.mapVertices((id, _) =>
      if (id == root) 0.0 else Double.PositiveInfinity)

    val bfs = initialGraph.pregel(Double.PositiveInfinity, maxIterations = 10)(
      (_, attr, msg) => math.min(attr, msg),
      triplet => {
        if (triplet.srcAttr != Double.PositiveInfinity) {
          Iterator((triplet.dstId, triplet.srcAttr + 1))
        } else {
          Iterator.empty
        }
      },
      (a, b) => math.min(a, b)).cache()
    bfs
  }

  def degreeOfSeparationSingleUser(root: VertexId): Array[(VertexId, DegreeOfSeparation)] = {
    getBFS(root).vertices.join(MyVerts).take(100)
  }

  def degreeOfSeparationTwoUser(firstUser: VertexId, secondUser: VertexId): Array[Double] = {
    getBFS(firstUser)
      .vertices
      .filter { case (vertexId, _) => vertexId == secondUser }
      .collect.map { case (_, degree) => degree }
  }

  def connectedComponent: VertexRDD[VertexId] = graph.connectedComponents().vertices

  def connectedComponentGroupedByUsers: RDD[(String, VertexId)] =
    MyVerts.join(connectedComponent).map {
      case (_, (username, comp)) => (username, comp)
    }

  def socialGraphTriangleCount: Graph[PartitionID, String] = graph.triangleCount()
}
