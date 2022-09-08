package com.lhm.graphx.page_rank

import com.lhm.graphx.page_rank.pregel.{InputDataFlow, SocialGraph}
import org.apache.spark.sql.SparkSession

/**
 * @author: 李煌民
 * @date: 2022-09-02 10:08
 *        ${description}
 **/
object Demo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getSimpleName)
      .master("local[*]")
      .getOrCreate()

    spark
      .read
      .format("csv")
      .option("header", "true")

    spark.close()
  }
}
