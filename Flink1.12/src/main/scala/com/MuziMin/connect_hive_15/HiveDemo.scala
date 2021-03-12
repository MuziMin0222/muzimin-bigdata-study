package com.MuziMin.connect_hive_15

import org.apache.flink.table.api.{EnvironmentSettings, TableEnvironment}
import org.apache.flink.table.catalog.hive.HiveCatalog

/**
 * @author : 李煌民
 * @date : 2021-03-04 17:57
 *       ${description}
 **/
object HiveDemo {
  def main(args: Array[String]): Unit = {
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().build()
    val tenv = TableEnvironment.create(settings)

    val name = "myhive"
    val defaultDatabase = "default"
    val hiveConfDir = "D:\\code\\workspace_IdeaUi\\RoadToBigData\\Flink1.12\\conf"

    val hive = new HiveCatalog(name, defaultDatabase, hiveConfDir)
    tenv.registerCatalog("myhive", hive)
    tenv.useCatalog("myhive")

    val sql = "insert into tbl2 values(3,'lhm',990)"
    val result = tenv.executeSql(sql)

    println(result.getJobClient.get().getJobStatus)
  }
}
