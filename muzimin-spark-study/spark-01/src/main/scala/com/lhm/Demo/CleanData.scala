package com.lhm.Demo

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._

/**
 * @author : 李煌民
 * @date : 2020-10-30 15:35
 *       ${description}
 **/
object CleanData {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("demo")
      .master("local[*]")
      .getOrCreate()

    val sourceDF = spark
      .read
      .format("csv")
      .options(
        Map(
          "quote" -> "\"", //设置CSV每个字段的包裹符
          "escape" -> "\"", //设置csv文件中的字段存在双引号，进行转义处理
          "quoteAll" -> "true", //设置整个csv文件进行双引号全包裹
          "header" -> "true" //默认CSV第一行就是表头
        ))
      .load("/Users/muzimin/Downloads/1111.csv")
      .filter(col("_raw") =!= "\"")

    sourceDF
      .withColumn("time", regexp_replace(split(split(col("_raw"), " ")(0), "=")(1), "\"", ""))
      .withColumn("level", split(split(col("_raw"), " ")(1), "=")(1))
      .withColumn("msg", regexp_replace(split(col("_raw"), "msg=")(1), "\"", ""))
      .withColumn("流程ID", split(split(col("msg"), ",")(0), ":")(1))
      .withColumn("组件ID", split(split(col("msg"), ",")(1), ":")(1))
      .withColumn("msg", split(col("msg"), ",")(2))
      .select("time", "level", "流程ID", "组件ID", "msg")
      .repartition(1)
      .write
      .format("csv")
      .option("header","true")
      .option("delimiter","\t")
      .save("/Users/muzimin/Downloads/log")

    spark.close()
  }

}
