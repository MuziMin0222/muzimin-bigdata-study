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
      .option("url", "jdbc:oracle:thin:@10.99.189.138:1521:hrtest")
      .option("dbtable", "(select * from SHR0309.T_ORG_ADMIN) source")
      .option("user", "JTCS")
      .option("password", "JTCS2435hr")
      .load()

    dataFrame.show()
  }


}
