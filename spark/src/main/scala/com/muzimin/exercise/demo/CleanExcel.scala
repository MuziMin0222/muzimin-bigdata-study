package com.muzimin.exercise.demo

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/**
 * @author : 李煌民
 * @date : 2021-04-23 15:41
 *       ${description}
 **/
object CleanExcel {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    val sourceDF = spark.read
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
      .load("D:\\关联设备.xlsx")
      .na.fill("")

    val resDF = sourceDF.withColumn("检测项目管理编号",
      explode(
        split(
          regexp_replace(
            regexp_replace(col("检测项目管理编号"), "；", ";"), "/", ";"), ";")))
      .withColumn("设备编号",
        explode(
          split(
            regexp_replace(col("设备编号"), "；", ";"), ";")))



    save("D:\\res.xlsx", resDF)
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
      //      .option("dataAddress", "'Sheet'!A1:E2")
      .option("useHeader", "true")
      //.option("dateFormat", "yy-mmm-d") // Optional, default: yy-m-d h:mm
      //.option("timestampFormat", "mm-dd-yyyy hh:mm:ss") // Optional, default: yyyy-mm-dd hh:mm:ss.000
      .mode("overwrite") // Optional, default: overwrite.
      .save(filePath)
  }
}
