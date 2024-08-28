package com.muzimin

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.influxdb.InfluxDBFactory
import org.influxdb.dto.Query

import java.text.SimpleDateFormat
import java.util
import java.util.Date
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters.asScalaBufferConverter

/**
 * @author: 李煌民
 * @date: 2024-08-28 13:44
 *        ${description}
 * */
object ReadInfluxdbToStarrocks {
  def main(args: Array[String]): Unit = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val startTime = "2022-01-27T04:30:00.000Z"
    val endTime = "2024-01-01T00:00:00.000Z"
    var startTimeStamp = format.parse(startTime).getTime
    val endTimeStamp = format.parse(endTime).getTime
    val offset = 10 * 60 * 1000

    val serverUrl = "http://172.18.8.36:8086"
    val username = "dashan_readonly"
    val password = "123456"

    val influxDB = InfluxDBFactory.connect(serverUrl, username, password)
    influxDB.setDatabase("RealtimeData")

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("demo")
      .getOrCreate()
    val sc = spark.sparkContext

    while (startTimeStamp <= endTimeStamp) {
      val startParseTime = format.format(new Date(startTimeStamp))

      startTimeStamp = startTimeStamp + offset
      val endParseTime = format.format(new Date(startTimeStamp))

      println(s"开始执行${startParseTime} -> ${endParseTime}")
      val sql =
        s"""
           |SELECT * FROM a_data WHERE time >= '$startParseTime' and time < '$endParseTime'
           |and (
           |tag_name = 'DSXC_BZM_SAG_IFDB_FEEDTOTAL_PV' or
           |tag_name = 'DSXC_BZM_SAG_IFDB_SAGSTATUS_TOT' or
           |tag_name = 'DSXC_MF_QM7_IFDB_FEEDTOTAL_PV' or
           |tag_name = 'DXSC_MF_QM7_M7_RUNTIME_TOT_PV' or
           |tag_name = 'DSXC_MF_QM4_IFDB_FEEDTOTAL_PV' or
           |tag_name = 'DXSC_MF_QM4_M4_RUNTIME_TOT_PV'
           |)
           |""".stripMargin
      val queryResult = influxDB.query(new Query(sql))
      val results = queryResult.getResults
      val framesList = new ListBuffer[DataFrame]
      for (result <- results.asScala.toList) {
        val series = result.getSeries
        if (series != null) {
          for (series1 <- series.asScala.toList) {
            val values: util.List[util.List[AnyRef]] = series1.getValues
            val valuesList = values.asScala.toList
            val dataList: List[data1] = valuesList.map(sublist => {
              val time = String.valueOf(sublist.get(0))
              val Value = String.valueOf(sublist.get(1))
              val quality = String.valueOf(sublist.get(2))
              val tagName = String.valueOf(sublist.get(3))

              data1(time, Value, quality, tagName)
            })
              .filter(item => {
                List(
                  "DSXC_BZM_SAG_IFDB_FEEDTOTAL_PV",
                  "DSXC_BZM_SAG_IFDB_SAGSTATUS_TOT",
                  "DSXC_MF_QM7_IFDB_FEEDTOTAL_PV",
                  "DXSC_MF_QM7_M7_RUNTIME_TOT_PV",
                  "DSXC_MF_QM4_IFDB_FEEDTOTAL_PV",
                  "DXSC_MF_QM4_M4_RUNTIME_TOT_PV"
                ).contains(item.tag_name)
              })

            import spark.implicits._
            val df = sc.makeRDD(dataList).toDF()

            framesList.append(df)
          }
        }
      }

      if (framesList.nonEmpty) {
        val resultDF = framesList.reduce(_.unionAll(_))

        resultDF
          .write.format("starrocks")
          .option("starrocks.fe.http.url", "10.99.190.212:8029")
          .option("starrocks.fe.jdbc.url", "jdbc:mysql://10.99.190.212:9030")
          .option("starrocks.table.identifier", "dsxk.t_influxdb_data")
          .option("starrocks.user", "root")
          .option("starrocks.password", "Sjzt@2024test")
          .mode("append")
          .save()

        framesList.clear()
      }
    }
  }
}

case class data1(
                  time: String,
                  Value: String,
                  quality: String,
                  tag_name: String
                )
