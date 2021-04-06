package com.hypers.exercise.vhall

import java.io.File
import java.util

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-03-31 13:38
 *       ${description}
 **/
object UserAction {
  private var count = 0
  private var list = new util.ArrayList[DataFrame]()

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    process("D:\\Hypers\\data", spark)

  }

  def process(filePath: String, spark: SparkSession): Unit = {
    val file = new File(filePath)
    if (file.exists) { //如果该目录存在
      if (!file.isDirectory) { //如果是文件，输出目录路径
        System.out.println("是文件")
        val absolutePath = file.getAbsolutePath
        System.out.println("path=" + file.getPath)
        count += 1
        val outPutPath = file.getName.substring(0, file.getName.lastIndexOf(".")).concat("_" + count).concat(".csv")
        val outPath = "D:\\res\\" + outPutPath
        load(absolutePath, spark, outPath)
      }
      else {
        System.out.println("是文件夹！")
        val fileList = file.listFiles
        if (fileList != null && fileList.length > 0) for (file_ <- fileList) {
          val absolutePath = file_.getAbsolutePath
          if (!file_.isDirectory) if (absolutePath.endsWith("csv")) {
            System.out.println("删除的文件" + absolutePath)
            new File(absolutePath).deleteOnExit()
          }
          else {
            System.out.println("path=" + absolutePath)
            count += 1
            val outPutPath = file_.getName.substring(0, file_.getName.lastIndexOf(".")).concat("_" + count).concat(".csv")
            val outPath = "D:\\res\\" + outPutPath
            load(absolutePath, spark, outPath)
          }
          else if (file_.isDirectory) { //如果不是文件，而是文件夹的话，则返回去重新执行readFile方法(迭代)
            process(absolutePath, spark)
          }
        }
        else System.out.println("该目录下面为空！")
      }
    }
    else if (!file.exists) { //该目录不存在
      System.out.println("该目录或文件不存在")
    }
  }

  /**
   * 加载Excel数据
   *
   * @param filePath 文件路今天
   * @param spark    SparkSession
   */
  def load(filePath: String, spark: SparkSession, outPath: String) = {
    var df = spark.read
      .format("com.crealytics.spark.excel")
      //.option("dataAddress", "'Sheet1'!A1:G2") // 可选,设置选择数据区域 例如 A1:E2。
      .option("useHeader", "true") // 必须，是否使用表头，false的话自己命名表头（_c0）,true则第一行为表头
      .option("treatEmptyValuesAsNulls", "true") // 可选, 是否将空的单元格设置为null ,如果不设置为null 遇见空单元格会报错 默认t: true
      .option("inferSchema", "true") // 可选, default: false
      //.option("addColorColumns", "true") // 可选, default: false
      //.option("timestampFormat", "yyyy-mm-dd hh:mm:ss") // 可选, default: yyyy-mm-dd hh:mm:ss[.fffffffff]
      //.option("excerptSize", 6) // 可选, default: 10. If set and if schema inferred, number of rows to infer schema from
      //.option("workbookPassword", "pass") // 可选, default None. Requires unlimited strength JCE for older JVMs====
      //.option("maxRowsInMemory", 20) // 可选, default None. If set, uses a streaming reader which can help with big files====
      //.schema(schema) // 可选, default: Either inferred schema, or all columns are Strings
      .load(filePath)

    if (outPath.substring(outPath.lastIndexOf("\\") + 1, outPath.lastIndexOf("_")).equals("user_action")) {
      println("action输出的路径是：" + outPath)
      df = df.withColumn("action_start_time", from_unixtime(unix_timestamp(col("action_start_time"), "MM/dd/yy HH:mm"), "yyyy-MM-dd HH:mm:ss"))
        .withColumn("industry_tag", lit(""))
        .withColumn("product_tag", lit(""))
        .withColumn("solution_tag", lit(""))
        .withColumn("topic_tag", lit(""))
        .withColumn("nick_name", lit(""))
        .select(
          "contact_id",
          "open_id",
          "mobile_number",
          "email",
          "action_start_time",
          "action_end_time",
          "duration",
          "action_name",
          "content_id",
          "content_name",
          "industry_tag",
          "product_tag",
          "solution_tag",
          "topic_tag",
          "campaign_id",
          "platform_name",
          "device_platform",
          "nick_name"
        )
    }

    if (outPath.substring(outPath.lastIndexOf("\\") + 1, outPath.lastIndexOf("_")).equals("campaign")) {
      println("campaign输出的路径是：" + outPath)
      df = df
        .withColumn("campaign_start_time", from_unixtime(unix_timestamp(col("campaign_start_time"), "MM/dd/yy HH:mm"), "yyyy-MM-dd HH:mm:ss"))
        .withColumn("campaign_end_time", from_unixtime(unix_timestamp(col("campaign_end_time"), "MM/dd/yy HH:mm"), "yyyy-MM-dd HH:mm:ss"))
        .withColumn("campaign_location", lit(""))
        .withColumn("industry_tag", lit(""))
        .withColumn("product_tag", lit(""))
        .withColumn("solution_tag", lit(""))
        .withColumn("topic_tag", lit(""))
        .withColumn("campaign_org_name", lit(""))
        .withColumn("bg", lit("cloud"))
        .withColumn("campaign_type", lit(""))
        .select(
          "campaign_id",
          "campaign_name",
          "campaign_start_time",
          "campaign_end_time",
          "campaign_location",
          "industry_tag",
          "product_tag",
          "solution_tag",
          "topic_tag",
          "campaign_org_name",
          "bg",
          "campaign_type"
        )
    }

    if (outPath.substring(outPath.lastIndexOf("\\") + 1, outPath.lastIndexOf("_")).equals("survey")) {
      println("survey输出的路径是：" + outPath)
      df = df
        .select(
          "contact_id",
          "mobile_number",
          "email",
          "action_start_time",
          "campaign_id",
          "content_id",
          "content_name",
          "platform_name",
          "survey"
        )
    }

    if (outPath.substring(outPath.lastIndexOf("\\") + 1, outPath.lastIndexOf("_")).equals("user")) {
      println("user输出的路径是：" + outPath)
      df = df
        .withColumn("l2_industry", lit(""))
        .withColumn("country", lit(""))
        .withColumn("is_na", lit(""))
        .withColumn("hw_relation", lit(""))
        .withColumn("nick_name", lit(""))
        .select(
          "mobile_number",
          "email",
          "contact_id",
          "full_name",
          "position",
          "l1_industry",
          "l2_industry",
          "country",
          "province",
          "city",
          "is_na",
          "hw_relation",
          "platform_name",
          "create_time",
          "update_time",
          "customer_name",
          "nick_name"
        )
    }

    df
      .na.fill("")
      .coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .option("header", "true")
      .option("sep", "\u0001")
      .csv(outPath)

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
      .option("dataAddress", "'Sheet'!A1:E2")
      .option("useHeader", "true")
      //.option("dateFormat", "yy-mmm-d") // Optional, default: yy-m-d h:mm
      //.option("timestampFormat", "mm-dd-yyyy hh:mm:ss") // Optional, default: yyyy-mm-dd hh:mm:ss.000
      .mode("append") // Optional, default: overwrite.
      .save(filePath)
  }
}
