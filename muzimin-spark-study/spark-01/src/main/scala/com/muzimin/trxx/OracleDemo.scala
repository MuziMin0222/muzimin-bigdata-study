package com.muzimin.trxx

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author: 李煌民
 * @date: 2024-04-09 08:28
 *        ${description}
 * */
object OracleDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

    val dataFrame: DataFrame = spark
      .read
      .format("jdbc")
      .option("driver", "oracle.jdbc.driver.OracleDriver")
      .option("url", "jdbc:oracle:thin:@{IP}:{port}:hrtest")
      .option("dbtable", "(select * from {schema_name}.{table_name}) source")
      .option("user", "{oracle_user}")
      .option("password", "{oracle_password}")
      .load()

    dataFrame.show()
  }


}
