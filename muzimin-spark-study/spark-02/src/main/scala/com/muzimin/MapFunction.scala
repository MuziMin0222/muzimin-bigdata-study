package com.muzimin

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author: 李煌民
 * @date: 2024-08-07 16:16
 *        ${description}
 * */
object MapFunction {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("demo")
      .getOrCreate()

    val sc = spark.sparkContext
    val value: RDD[(String, Iterable[String])] = sc.makeRDD(Seq(1, 2, 3, 4, 5))
      .map(v1 => {
        v1 + "111"
      })
      .flatMap(s => {
        List("1", "2", s)
      })
      .groupBy(a => {
        a
      })

    spark.stop()
  }

}
