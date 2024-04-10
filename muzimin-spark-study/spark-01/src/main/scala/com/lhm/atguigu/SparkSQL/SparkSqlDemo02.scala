package com.lhm.atguigu.SparkSQL

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object SparkSqlDemo02 {
  def main(args: Array[String]): Unit = {
    //SparkConf,创建配置对象
    val conf = new SparkConf().setAppName("SparkSqlDemo").setMaster("local[*]")

    //创建sparkSQL的环境对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //转换之前导入隐式转换规则，spark不是包名，而是sparkSession的变量名
    import spark.implicits._

    //创建RDD
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(Array((1,"lihuangmin",21),(2,"luzhen",22),(3,"xiaobaobei",21)))

    //RDD转为DataFrame
    val dataFrame: DataFrame = rdd.toDF("id","name","age")

    //DataFrame转为DataSet,那么必须要添加一个样例类
    val dataset: Dataset[Family] = dataFrame.as[Family]

    //DataSet转换成DataFrame
    val df = dataset.toDF()

    //DataFrame转换成RDD
    val rdd1 = df.rdd

    rdd1.foreach( row => {
      //getXXX中的参数是每一行中的元素的索引值
      print(row.getInt(0))
      println()
      print(row.getString(1))
      println()
      print(row.getInt(2))
    })

    //释放资源
    spark.stop()
  }
}

case class Family(id:Int,name:String,age:Int)
