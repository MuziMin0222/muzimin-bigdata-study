package com.lhm.Spark.SparkSql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object Demo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().config(new SparkConf().setAppName("Demo").setMaster("local[*]")).getOrCreate()
    import spark.implicits._

    val sourceRDD: RDD[String] = spark.sparkContext.textFile("data/a.json")

    val schemaString: String = "name age"

    val fields: Array[StructField] = schemaString.split(" ")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))

    val schema: StructType = StructType(fields)

    val rowRDD: RDD[Row] = sourceRDD
      .map(_.split(","))
      .map(attributes => Row(attributes(0), attributes(1).trim))

    val peopleDF: DataFrame = spark.createDataFrame(rowRDD, schema)

    peopleDF.createOrReplaceTempView("people")

    val results = spark.sql("SELECT name FROM people")
    results.map(attributes => "Name: " + attributes(0)).show()

  }
}
