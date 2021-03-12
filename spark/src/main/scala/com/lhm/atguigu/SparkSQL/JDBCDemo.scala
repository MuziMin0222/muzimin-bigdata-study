package com.lhm.atguigu.SparkSQL

import java.util.Properties

object JDBCDemo {
  def main(args: Array[String]): Unit = {
    //jdbc连接，加载数据，写入数据，方式一
    val dataFrame1 = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:8017/demo")
      .option("dbtable", "teacher")
      .option("user", "root")
      .option("password", "root")
      .load()
    dataFrame1.show()
    //写入数据
    dataFrame1.write
      .format("jdbc")
      .option("url","jdbc:mysql://127.0.0.1:8017/demo")
      .option("dbtable","teacher")
      .option("user","root")
      .option("password","root")
      .mode("append")
      .save()

    //jdbc连接，加载数据，写入数据，方式二
    val prop = new Properties()
    prop.put("user","root")
    prop.put("password","root")
    val dataFrame2 = spark.read.jdbc("jdbc:mysql://127.0.0.1:8017/demo","teacher",prop)
    dataFrame2.show()
    //写入数据,因为表存在，我们追加数据
    dataFrame2.write.mode("append")
      .jdbc("jdbc:mysql://127.0.0.1:8017/demo","teacher",prop)
  }
}
