package com.lhm.atguigu.SparkSQL

object LoadDemo {
  def main(args: Array[String]): Unit = {
    //单纯的使用load加载数据会报错，手动更改格式
    val dataFrame = spark.read.format("json").load("data/a.json")
    dataFrame.show()

    //默认保存文件是parquet格式,当文件保存过一次时，可以追加也可以覆盖,调用mode方法
    dataFrame.select("age","name").write.mode("append").save("data/nameAndAge.parquet")
    //可以手动更改保存数据的格式
    dataFrame.select("age","name").write.mode("overwrite").format("json").save("NameAndAge.json")

    //也可以创建一个临时窗口
    dataFrame.createTempView("user")
    spark.sql("select * from user").show
  }
}
