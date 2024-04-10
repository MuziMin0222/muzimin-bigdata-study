package com.muzimin.exercise.demo

import java.io.File

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

/**
 * @author : 李煌民
 * @date : 2021-11-03 10:54
 *       ${description}
 **/
object TechWaveMake {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Demo")
      .master("local[*]")
      .getOrCreate()

    val files = new File("D:\\2020年场次的报名数据（加了campaign_name）\\2020年场次的报名数据（加了campaign_name）").listFiles()
    val excelDF = files.map(
      file => {

        val sourceDF = spark.read
          .format("com.crealytics.spark.excel")
          .option("useHeader", "true")
          .option("treatEmptyValuesAsNulls", "true")
          .option("inferSchema", "true")
          .load(file.getAbsolutePath)

        sourceDF
      }
    )
      .reduce(_.union(_))
      .withColumn("campaign_start_time", split(col("campaign_start_time"), "T")(0))

    val actionDF = excelDF
      .select(
        lit("").as("contact_id"),
        lit("").as("open_id"),
        lit("").as("mobile_number"),
        col("email").as("email"),
        col("campaign_start_time").as("action_start_time"),
        lit("").as("action_end_time"),
        lit("").as("duration"),
        lit("注册参会").as("action_name"),
        md5(col("campaign_name")).as("content_id"),
        col("campaign_name").as("content_name"),
        lit("").as("industry_tag"),
        lit("").as("product_tag"),
        lit("").as("solution_tag"),
        lit("").as("topic_tag"),
        md5(col("campaign_name")).as("campaign_id"),
        lit("Sinobase").as("platform_name"),
        lit("").as("nick_name"),
        lit("").as("utm_campaign"),
        lit("").as("utm_content"),
        lit("").as("utm_medium"),
        lit("").as("utm_source"),
        lit("").as("utm_term"),
        lit("").as("utm_object"),
        lit("").as("register_source"),
        lit("").as("prefix_url"),
        lit("").as("content_type"),
        lit("").as("response_sourcetype"),
        lit("").as("register_region"),
        lit("").as("source"),
        lit("").as("uniportal_id"),
        lit("").as("full_name"),
        lit("").as("language"),
        lit("").as("form_product"),
        lit("").as("form_requirement")
      )

    actionDF
      .na.fill("")
      .coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .format("csv")
      .option("delimiter", "\u0001")
      .save("D:\\res\\action")

    val userDF = excelDF
      .select(
        lit("").as("mobile_number"),
        col("email").as("email"),
        lit("").as("contact_id"),
        col("name").as("full_name"),
        lit("").as("position"),
        col("Industry").as("l1_industry"),
        lit("").as("l2_industry"),
        lit("").as("country"),
        lit("").as("province"),
        lit("").as("city"),
        lit("").as("is_na"),
        lit("").as("hw_relation"),
        lit("Sinobase").as("platform_name"),
        col("campaign_start_time").as("create_time"),
        col("campaign_start_time").as("update_time"),
        lit("").as("customer_name"),
        lit("").as("is_agree"),
        lit("").as("nick_name"),
        lit("").as("zixun_partners"),
        lit("").as("zongji_partners"),
        lit("").as("ruodian_partners"),
        lit("").as("from_tianyancha"),
        lit("").as("card_type"),
        lit("").as("badge_name"),
        lit("").as("is_accept_phone"),
        lit("").as("is_accept_email"),
        lit("").as("first_name"),
        lit("").as("last_name"),
        lit("").as("hw_id")
      )

    userDF
      .coalesce(1)
      .na.fill("")
      .write
      .mode(SaveMode.Overwrite)
      .format("csv")
      .option("delimiter", "\u0001")
      .save("D:\\res\\user")


    val campaignDF = excelDF
      .select(
        md5(col("campaign_name")).as("campaign_id"),
        col("campaign_name").as("campaign_name"),
        col("campaign_start_time").as("campaign_start_time"),
        lit("").as("campaign_end_time"),
        lit("").as("campaign_location"),
        lit("").as("industry_tag"),
        lit("").as("product_tag"),
        lit("").as("solution_tag"),
        lit("").as("topic_tag"),
        lit("").as("campaign_org_name"),
        lit("cloud").as("bg"),
        lit("").as("campaign_type"),
        lit("").as("tactic_code"),
        lit("").as("campaign_creator"),
        lit("").as("second_scene"),
        lit("").as("third_label")
      )
      .distinct()

    campaignDF
      .coalesce(1)
      .na.fill("")
      .write
      .mode(SaveMode.Overwrite)
      .format("csv")
      .option("delimiter", "\u0001")
      .save("D:\\res\\campaign")


    spark.close()
  }
}
