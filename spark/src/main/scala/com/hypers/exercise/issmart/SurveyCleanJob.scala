package com.hypers.exercise.issmart

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

/**
 * @author : 李煌民
 * @date : 2021-04-07 14:53
 *       ${description}
 **/
object SurveyCleanJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    val sourceDF = spark.read
      .format("com.crealytics.spark.excel")
      //.option("dataAddress", "'（CDP ETL使用）新增标签规则'") // 可选,设置选择数据区域 例如 A1:E2。
      .option("useHeader", "true") // 必须，是否使用表头，false的话自己命名表头（_c0）,true则第一行为表头
      .option("treatEmptyValuesAsNulls", "true") // 可选, 是否将空的单元格设置为null ,如果不设置为null 遇见空单元格会报错 默认t: true
      .option("inferSchema", "true") // 可选, default: false
      //.option("addColorColumns", "true") // 可选, default: false
      //.option("timestampFormat", "yyyy-mm-dd hh:mm:ss") // 可选, default: yyyy-mm-dd hh:mm:ss[.fffffffff]
      //.option("excerptSize", 6) // 可选, default: 10. If set and if schema inferred, number of rows to infer schema from
      //.option("workbookPassword", "pass") // 可选, default None. Requires unlimited strength JCE for older JVMs====
      //.option("maxRowsInMemory", 20) // 可选, default None. If set, uses a streaming reader which can help with big files====
      //.schema(schema) // 可选, default: Either inferred schema, or all columns are Strings
      .load("D:\\分析项.xlsx")
      .na.fill("")
      .withColumn("rule_name", concat_ws("-", split(col("l1"), "-")(0), col("l2")))
      .withColumn("rule_value", concat_ws("=>", col("rule"), col("c2")))
      .groupBy("rule_name")
      .agg(
        concat_ws("|", collect_set(col("rule_value"))).as("rule_value")
      )
      .orderBy("rule_name")

    sourceDF
      .coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .option("header", "true")
      .option("delimiter", "\t")
      .csv("D:\\rule.csv")

  }


}
