package com.muzimin.exercise.demo

import java.text.SimpleDateFormat
import java.util.Locale

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

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
      .load("D:\\add.xlsx")
      .na.fill("")
      .withColumn("pubdate", parseDate(col("pubdate")))

    val countryDF = spark.read
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
      .load("D:\\全球国家信息表（英文名）.xlsx")
      .na.fill("")
      .select(lower(col("国家或地区英文名")).as("国家或地区英文名"), lower(col("代码")).as("代码"))

    sourceDF.show()

    val resDF = sourceDF.join(countryDF, col("国家或地区英文名") === lower(col("country")), "left")
      .withColumn("country", when(col("代码").isNotNull, col("代码")).otherwise(col("country")))
      .select(
        "document_id",
        "title",
        "url",
        "media_type",
        "country",
        "sentiment",
        "keywords",
        "channel",
        "region",
        "pubdate",
        "city",
        "company",
        "industry",
        "product",
        "sentence",
        "reach",
        "source_type",
        "phrases"
      )

    resDF.show()

    resDF.coalesce(1)
        .write
        .format("csv")
        .option("header","true")
        .option("delimiter","\u0001")
        .save("D:\\resDF.csv")

    //save("D:\\resDF.xlsx", resDF)
  }

  val parseDate = udf(
    (dateTime: String) => {
      val format = new SimpleDateFormat("dd-MMM-yyyy hh:mma", Locale.US)
      val parse = format.parse(dateTime)
      val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")

      dateFormat.format(parse)
    }
  )


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
