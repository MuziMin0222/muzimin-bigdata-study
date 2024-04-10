package com.lhm.graphx.page_rank.pregel

import org.apache.spark.graphx.{Edge, VertexId}

import scala.collection.mutable.ListBuffer

/**
 * @author: 李煌民
 * @date: 2022-08-31 15:49
 *        ${description}
 **/
object InputDataFlow {
  def parseNames(line: String): Option[(VertexId, String)] = {
    val fields = line.split('\t')
    if (fields.length > 1)
      Some(fields(0).trim().toLong, fields(1))
    else None
  }

  def makeEdges(line: String): List[Edge[Int]] = {
    var edges = new ListBuffer[Edge[Int]]()
    val fields = line.split(" ")
    val origin = fields(0)
    (1 until fields.length)
      .foreach { p =>
        edges += Edge(origin.toLong, fields(p).toLong, 0)
      }
    edges.toList
  }

  def myMakeEdges(line: String): List[Edge[String]] = {
    var edges = new ListBuffer[Edge[String]]()
    val fields = line.split(",")
    val origin = fields(0)
    (1 until fields.length)
      .foreach {
        p => {
          if (!fields(p).equals("")) {
            edges += Edge(origin.hashCode.toLong, fields(p).hashCode.toLong, s"$origin -> ${fields(p)}")
          }
        }
      }
    edges.toList
  }
}
