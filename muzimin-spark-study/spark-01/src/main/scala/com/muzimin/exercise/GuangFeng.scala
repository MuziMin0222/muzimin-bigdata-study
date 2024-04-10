package com.muzimin.exercise

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions._

/**
 * @author : 李煌民
 * @date : 2021-03-31 10:29
 *       ${description}
 **/
object GuangFeng {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()
    val dmpRDD = spark.sparkContext.textFile("D:\\dmp.csv")
    val hypersRDD = spark.sparkContext.textFile("D:\\hypers.csv")

    import spark.implicits._

    val dmpDF: DataFrame = dmpRDD
      .map(
        line => {
          val lineArr = line.split("\t", -1)
          val Array(nginx_time, p_user_id, act_name, p_payment_type, nginx_date) = lineArr
          DmpBean(nginx_time, p_user_id, act_name, p_payment_type, nginx_date)
        }
      ).toDF
      .withColumnRenamed("nginx_time", "dmp_nginx_time")

    val hypersDF: DataFrame = hypersRDD.map(
      line => {
        val lineArr = line.split("\t", -1)
        val Array(date, channel, type_name, nginx_time, user_id, event, platform) = lineArr
        MuziminBean(date, channel, type_name, nginx_time, user_id, event, platform)
      }
    ).toDF()
      .withColumn("nginx_time", from_unixtime(col("nginx_time")/1000, "yyyy-MM-dd HH:mm:ss"))

    val resDF = hypersDF.join(dmpDF, col("user_id") === col("p_user_id"), "left")
      .filter(col("p_user_id").isNull)
      .select("channel", "nginx_time", "user_id", "event")
    resDF
      .coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .option("header","true")
      .option("sep","\t")
      .csv("D:\\res")

  }
}
