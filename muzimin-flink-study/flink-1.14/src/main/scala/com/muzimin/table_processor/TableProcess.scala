package com.muzimin.table_processor

import org.apache.flink.table.api.{EnvironmentSettings, TableEnvironment}

/**
 * @author: 李煌民
 * @date: 2024-04-10 16:21
 *        ${description}
 * */
object TableProcess {
  def main(args: Array[String]): Unit = {
    //开启批处理操作
    /*val settings = EnvironmentSettings.inBatchMode()
    val tenv = TableEnvironment.create(settings)

    tenv.executeSql(
      s"""
         |create table demo
         |(
         |    user_id string,
         |    user_name string,
         |    user_status string
         |)
         |WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/muzimin?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true',
         |   'table-name' = 't_book',
         |   'username' = 'root',
         |   'password' = '123456'
         |)
         |""".stripMargin)

      tenv.executeSql("select * from demo").print()*/
  }

}
