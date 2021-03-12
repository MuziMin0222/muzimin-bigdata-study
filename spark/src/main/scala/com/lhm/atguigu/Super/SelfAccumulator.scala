package com.lhm.atguigu.Super

import java.util

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.util.AccumulatorV2

object SelfAccumulator {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(Array("lihuangmin", "lhm", "lz", "luzhen", "spark"))

    //TODO 创建累加器
    val accumulator = new MyAccumulator
    //TODO 注册累加器
    sc.register(accumulator)
    sourceRDD.foreach {
      case str => {
        //TODO 执行累加器的累加功能
        accumulator.add(str)
      }
    }
    //TODO 获取累机器的值
    println("sum = " + accumulator.value)
  }
}

//自定义累加器
//继承AccumulatorV2抽象类
//实现抽象方法
class MyAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {
  val list = new util.ArrayList[String]()

  //当前累加器的初始状态
  override def isZero: Boolean = {
    list.isEmpty
  }

  //复制累加器对象
  override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
    new MyAccumulator
  }

  //重置累加器对象
  override def reset(): Unit = {
    list.clear()
  }

  //向累加器中添加元素
  override def add(v: String): Unit = {
    if (v.contains("l")) {
      list.add(v)
    }
  }

  //合并多个累加器的结果
  override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = {
    list.addAll(other.value)
  }

  //获取累加器的结果
  override def value: util.ArrayList[String] = {
    list
  }
}
