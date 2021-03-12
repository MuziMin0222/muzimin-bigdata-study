package com.lhm.atguigu.SparkSQL

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}

//用户自定义聚合函数
object UDFDemo2 {
  def main(args: Array[String]): Unit = {
    //SparkConf,创建配置对象
    val conf = new SparkConf().setAppName("SparkSqlDemo").setMaster("local[*]")

    //创建sparkSQL的环境对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //转换之前导入隐式转换规则，spark不是包名，而是sparkSession的变量名
    import spark.implicits._

    //创建DataFrame
    val dataFrame = spark.read.json("data/a.json")

    //自定义聚合函数，创建聚合函数对象
    val avgFunction = new MyAvgFunction
    //注册聚合函数
    spark.udf.register("avgAge",avgFunction)
    //创建视图
    dataFrame.createTempView("user")

    //使用聚合函数对age求平均值
    spark.sql("select avgAge(age) from user").show
  }
}

//声明用户自定义聚合函数实现求平均值
//继承UserDefinedAggregateFunction
class MyAvgFunction extends UserDefinedAggregateFunction {
  //函数输入的数据结构
  override def inputSchema: StructType = {
    //字段和字段类型
    new StructType().add("age", LongType)
  }

  //计算时的数据结构
  override def bufferSchema: StructType = {
    new StructType().add("sum", LongType).add("count", LongType)
  }

  //函数返回的数据类型
  override def dataType: DataType = {
    DoubleType
  }

  //函数是否和要求的输出类型一致
  override def deterministic: Boolean = true

  //计算之前的缓冲区的初始值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    //sum的初始值，buffer里面的参数对应数据结构的索引值
    buffer(0) = 0L;
    //count的初始值
    buffer(1) = 0L;
  }

  //根据查询结果更新缓冲区数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    //sum
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    //count
    buffer(1) = buffer.getLong(1) + 1
  }

  //将多个节点的缓冲区合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //sum
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    //count
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  //计算逻辑
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0) / buffer.getLong(1).toDouble
  }
}