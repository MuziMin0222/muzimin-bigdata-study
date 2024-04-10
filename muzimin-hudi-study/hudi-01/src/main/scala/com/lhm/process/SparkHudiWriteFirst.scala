package com.lhm.process

import java.util.{Random, UUID}

import com.lhm.bean.WebLog
import org.apache.commons.lang.RandomStringUtils
import org.apache.hudi.DataSourceWriteOptions._
import org.apache.hudi.QuickstartUtils._
import org.apache.hudi.config.HoodieWriteConfig._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode._

/**
 * @author : 李煌民
 * @date : 2021-02-18 16:01
 *       ${description}
 **/
object SparkHudiWriteFirst {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.default.parallelism", 4)
      .appName("Spark Hudi Load")
      .getOrCreate()

    // 构建元数据
    val tableName = "hudi_weblog"
    val hdfsPath = "/datalake/test/hudi_first"

    // 基于内存测试数据构建DataFrame
    // 生成10000条测试数据
    val MAX_DATA_NUM = 10000
    val r = new Random()

    val logList = (1 to MAX_DATA_NUM)
      .map(
        n => {
          val ip = (1 to 4)
            .map(x => (r.nextInt(255) + 1) % 255)
            .toList
            .mkString(".")
          val id = UUID.randomUUID().toString
          val url = s"/pro/goods/${RandomStringUtils.randomAlphanumeric(10)}.html"
          val date = s"2021-${(r.nextInt(13) + 1) % 13}-${(r.nextInt(32) + 1) % 32}"

          WebLog(ip, id, url, date)
        }
      )

    import spark.implicits._
    val weblogDF = spark.createDataFrame(logList)
      .select(
        $"ip",
        $"id",
        $"url",
        $"date",
        $"date".substr(0, 4).as("year"))

    weblogDF
      .write
      .format("hudi")
      .options(getQuickstartWriteConfigs)
      .option(PRECOMBINE_FIELD_OPT_KEY, "date")
      .option(RECORDKEY_FIELD_OPT_KEY, "id")
      .option(PARTITIONPATH_FIELD_OPT_KEY, "year")
      .option(TABLE_NAME, tableName)
      .mode(Overwrite)
      .save(hdfsPath)

  }
}
