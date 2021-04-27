package com.hypers.exercise.demo

import java.util

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.JavaConverters._

/**
 * @author : 李煌民
 * @date : 2021-04-25 15:45
 *       ${description}
 **/
object FlatMapExcel {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    val sourceDF = spark.read
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
      .load("D:\\res.xlsx")

    val configDF = spark.read
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
      .load("D:\\conf.xlsx")

    val countConfDf = configDF.groupBy("检测项目管理编号")
      .agg(
        count(lit(1)).as("count")
      )

    val joinDF = sourceDF.join(countConfDf, Seq("检测项目管理编号"), "left")
      .na.fill("")

    joinDF.select("count").filter(col("count") > 1).show()

    val resRDD = joinDF.rdd.flatMap(line => {
      val count = if (line.size >= 16) {
        try {
          line.getLong(15).toInt
        }catch {
          case e:Exception => {
            1
          }
        }
      } else {
        1
      }

      val list = new util.ArrayList[resEntity]()

      for (i <- 1 to count) {
        list.add(resEntity(
          line.getString(0), line.getString(1), line.getString(2), line.getString(3), line.getString(4), line.getString(5), line.getString(6), line.getString(7), line.getString(8), line.getString(9), line.getString(10), line.getString(11), line.getString(12), line.getString(13), line.getString(14)
        ))
      }

      list.asScala.toList
    })

    resRDD.take(10).foreach(println(_))

    import spark.implicits._

    val resDF = resRDD.toDF

    resDF.filter(col("检测项目管理编号") === "I4002").show()

    save("D:\\final_res.xlsx", resDF)
  }


  /**
   * 将数据保存到Excel文件中
   *
   * @param filePath 保存路径
   * @param df       数据集
   */
  def save(filePath: String, df: DataFrame): Unit = {
    df.write
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true")
      .mode("append")
      .save(filePath)
  }
}


case class resEntity(
                      检测项目管理编号: String,
                      分析项组1: String,
                      分析项组2: String,
                      分析项名称: String,
                      修约规则: String,
                      计算公式: String,
                      重复次数: String,
                      结果类型: String,
                      结果单位: String,
                      合格参考照片: String,
                      缺陷参考照片: String,
                      测试应力: String,
                      低限: String,
                      高限: String,
                      可能结果: String
                    )
