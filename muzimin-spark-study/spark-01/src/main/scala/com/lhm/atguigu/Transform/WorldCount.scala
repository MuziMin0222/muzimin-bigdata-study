package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

object WorldCount {
  def main(args: Array[String]): Unit = {
    //设定spark计算框架和运行部署环境
    val conf = new SparkConf().setMaster("local[*]").setAppName("worldCount")

    //创建spark上下文对象
    val sc = new SparkContext(conf)

    //读取文件，将文件内容一行一行的读取出来
    val lines = sc.textFile("file:///home/lhm/data")

    //将一行一行分解成单个的单词
    val words = lines.flatMap(_.split(" "))

    //为了统计方便，将单词数据进行结构的转化,转成一个二元元组
    val wordToOne = words.map((_,1))

    //对转化结构后的数据进行分组聚合
    val wordsToSum = wordToOne.reduceByKey(_+_)

    //将结果打印在控制台
    val res = wordsToSum.collect()
    res.foreach(println)
  }
}
