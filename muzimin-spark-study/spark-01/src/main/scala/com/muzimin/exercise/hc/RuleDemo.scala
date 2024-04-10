package com.muzimin.exercise.hc

import org.apache.spark.sql.functions.{col, collect_set, concat_ws}
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-08-04 14:11
 *       ${description}
 **/
object RuleDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    val sourceDF = spark.read
      .format("com.crealytics.spark.excel")
      .option("useHeader", "true") // 必须，是否使用表头，false的话自己命名表头（_c0）,true则第一行为表头
      .option("treatEmptyValuesAsNulls", "true") // 可选, 是否将空的单元格设置为null ,如果不设置为null 遇见空单元格会报错 默认t: true
      .option("inferSchema", "true") // 可选, default: false
      .load("D:\\Hypers\\华为\\hc\\hc_rule.xlsx")
      .na.fill("")
      .withColumn("rule_value", concat_ws("=>", col("rule"), col("c2")))
      .groupBy("rule_name")
      .agg(
        concat_ws("|", collect_set(col("rule_value"))).as("rule_value")
      )

    sourceDF.show()


    sourceDF
      .coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .option("header", "true")
      .option("delimiter", "\t")
      .csv("D:\\Hypers\\华为\\ecRule")

  }
}
