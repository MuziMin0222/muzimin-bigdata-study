package com.muzimin.exercise

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions._

/**
 * @author : 李煌民
 * @date : 2021-08-17 14:51
 *       ${description}
 **/
object CleanCampaignExcel {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    val sourceDF = spark.read
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
      .load("D:\\2021华为金融运维峰会-报名人员.xlsx")
      .na.fill("")

    val resDF = sourceDF
      .withColumn("城市", when(size(split(col("省份"), "-")) === 2, split(col("省份"), "-")(1)).otherwise(col("省份")))
      .withColumn("省份", when(size(split(col("省份"), "-")) === 2, split(col("省份"), "-")(0)).otherwise(col("省份")))
      .withColumn("l1_industry", when(size(split(col("*行业"), "-")) > 1, split(col("*行业"), "-")(0)).otherwise(col("*行业")))
      .withColumn("l2_industry", when(size(split(col("*行业"), "-")) > 1, split(col("*行业"), "-")(1)).otherwise(col("*行业")))

    resDF.show()

    save("D:\\acter_2021华为金融运维峰会-报名人员.xlsx", resDF)

    spark.close()
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
      .mode(SaveMode.Overwrite)
      .save(filePath)
  }
}
