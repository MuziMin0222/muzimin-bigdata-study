package com.MuziMin.cdc_16

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

/**
 * @author : 李煌民
 * @date : 2021-08-17 22:28
 *       ${description}
 **/
object TableCDCDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tenv = StreamTableEnvironment.create(env, settings)

    tenv.executeSql(
      s"""
         |CREATE TABLE mysql_binlog (
         | id INT NOT NULL,
         | name STRING,
         | description STRING,
         | weight DECIMAL(10,3)
         |) WITH (
         | 'connector' = 'mysql-cdc',
         | 'hostname' = 'localhost',
         | 'port' = '8017',
         | 'username' = 'root',
         | 'password' = 'root',
         | 'database-name' = 'bigdata',
         | 'table-name' = 'products'
         |)
         |""".stripMargin)

    val table = tenv.sqlQuery(
      s"""
         |SELECT id, UPPER(name), description, weight FROM mysql_binlog
         |""".stripMargin)

    table.toRetractStream[Row].print()

    //tenv.execute(this.getClass.getSimpleName)
    env.execute()
  }
}
