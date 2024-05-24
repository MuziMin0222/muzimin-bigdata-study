package com.muzimin

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.col

import scala.concurrent.duration.FiniteDuration
import scala.reflect.runtime.universe.Try
import scala.util.{Failure, Success}

/**
 * @author: 李煌民
 * @date: 2024-05-24 08:54
 *        ${description}
 * */
object ProcessExcel {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("sample")
      .getOrCreate()

    val sourceDF = spark.read.format("excel")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("/Users/muzimin/Downloads/aaa.xlsx")
      .cache()

    val tableList = sourceDF
      .select("数据表")
      .distinct()
      .collect()
      .map(row => {
        val tableName = row.getAs[String]("数据表")
        val pattern = """^[^_]+_[^_]+_(.*)$""".r
        if (tableName != null && tableName.trim.nonEmpty) {
          pattern.findFirstMatchIn(tableName) match {
            case Some(m) => (tableName, m.group(1))
            case None => (tableName, tableName)
          }
        } else {
          (tableName, tableName)
        }
      })
      .distinct
      .filter(item => item._1 != null && item._1.trim.nonEmpty)

    tableList
      .foreach(tableName => {
        write(sourceDF, tableName, 0)
      })

    spark.stop()
  }

  def write(sourceDF: DataFrame, tableName: (String, String), index: Int): Unit = {
    try {
      val sheetName = if (index == 0) {
        tableName._2
      } else {
        val value = s"${tableName._2.substring(0, 30)}${index}"
        if (value.length > 31) {
          value.substring(0, 31 - index.toString.length) + index
        }
      }
      println(s"开始处理--------->：${sheetName}")
      sourceDF
        .filter(col("数据表") === tableName._1)
        .write
        .format("com.crealytics.spark.excel")
        .option("dataAddress", s"'${sheetName}'!A1")
        .option("useHeader", "false")
        .option("header", "true")
        .mode("append")
        .save("/Users/muzimin/Downloads/ccc.xlsx")
    } catch {
      case e: Exception => {
        write(sourceDF, tableName, index + 1)
      }
    }
  }
}
