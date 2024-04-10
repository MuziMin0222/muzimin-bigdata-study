package com.lhm.copyOnWrite

import org.apache.spark.sql.SparkSession

/**
 * @author : 李煌民
 * @date : 2021-02-19 11:12
 *       ${description}
 **/
object SelectDemo {
  def main(args: Array[String]): Unit = {

  }

  def selectData() = {
    val basePath = "/tmp/hudi"
    val spark = SparkSession
      .builder
      .appName("query insert")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .master("local[3]")
      .getOrCreate()

    val tripsSnapshotDF = spark
      .read
      .format("org.apache.hudi")
      .load(basePath + "/*/*")

    tripsSnapshotDF.show()
  }
}
