package com.lhm.atguigu.SparkSQL

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

object UDFDemo3 {
  def main(args: Array[String]): Unit = {
    //创建聚合函数对象
    val function = new MyAgeClassFunction

    //将聚合函数转换为查询列
    val avgCol = function.toColumn.name("aveAge")

    val dataFrame = spark.read.json("data/a.json")

    //转换之前导入隐式转换规则，spark不是包名，而是sparkSession的变量名
    import spark.implicits._
    val DS = dataFrame.as[UserBean]

    //应用函数
    DS.select(avgCol).show
  }
}

//定义Dataset的样例类
case class UserBean(name: String, age: BigInt)

//定义平均值的缓冲区
case class AvgBuffer(var sum: BigInt, var count: Int)

//声明用户自定义聚合函数
//1、继承Aggregator,第一个泛型是传入的数据类型，第二个是计算缓冲区，第三个是输出的类型
class MyAgeClassFunction extends Aggregator[UserBean, AvgBuffer, Double] {
  //初始化计算的值
  override def zero: AvgBuffer = {
    AvgBuffer(0,0)
  }

  //聚合数据
  override def reduce(b: AvgBuffer, a: UserBean): AvgBuffer = {
    b.sum = b.sum + a.age
    b.count = b.count + 1
    b
  }

  //缓冲区的合并操作
  override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    b1
  }

  //完成计算
  override def finish(reduction: AvgBuffer): Double = {
    reduction.sum.toDouble / reduction.count
  }

  //自定义类型时的转码
  override def bufferEncoder: Encoder[AvgBuffer] = {
    Encoders.product
  }

  //自带类型的转码
  override def outputEncoder: Encoder[Double] = {
    Encoders.scalaDouble
  }
}