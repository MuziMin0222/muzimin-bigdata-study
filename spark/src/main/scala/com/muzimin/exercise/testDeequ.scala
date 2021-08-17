package com.muzimin.exercise

import com.amazon.deequ.repository.ResultKey
import com.amazon.deequ.repository.memory.InMemoryMetricsRepository
import org.apache.spark.sql.SparkSession

/**
 * @author : 李煌民
 * @date : 2021-01-19 10:28
 *
 **/
object testDeequ {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Demo").master("local[*]").getOrCreate()

    val metricsRepository = new InMemoryMetricsRepository()
    println(metricsRepository)
    val yesterdaysKey = ResultKey(System.currentTimeMillis() - 24 * 60 * 1000)
    println(yesterdaysKey)
  }
}
