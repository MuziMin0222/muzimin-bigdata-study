package com.lhm.atguigu.Super

import org.apache.spark.{SparkConf, SparkContext}

object accumulatorDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List(1,2,3,4),2)
    //使用系统累加器共享变量，来累加数据
    //创建累加器对象
    val accumulator = sc.longAccumulator
    sourceRDD.foreach{
      case i => {
        accumulator.add(i)
      }
    }
    println("sum = " + accumulator.value)
  }
}
