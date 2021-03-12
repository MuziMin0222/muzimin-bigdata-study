package com.lhm.atguigu.Transform

import org.apache.spark.{SparkConf, SparkContext}

//需求：统计出每一个省份广告被点击次数的TOP3
//时间戳，省份，城市，用户，广告
//1516609143867 6 7 64 16
//1516609143869 9 4 75 18
//1516609143869 1 7 87 12
object AnliDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //读取数据
    val sourceRDD = sc.textFile("data/agent.log")

    //按照最小粒度聚合（（省份，广告），1）
    val MapRDD = sourceRDD.map {
      line =>
        val fields: Array[String] = line.split(" ")
        ((fields(1), fields(4)), 1)
    }

    //计算每个省中每个广告被点击的次数（（省份，广告），sum）
    val ReduceRDD = MapRDD.reduceByKey(_ + _)

    //将省份作为key，广告加点击数value（省份，（广告，sum））
    val MapValueRDD = ReduceRDD.map {
      x => (x._1._1, (x._1._2, x._2))
    }

    //将同一个省份的所有广告进行聚合（省份，List（（广告1，sum1），（广告2，sum2）。。。））
    val GroupRDD = MapValueRDD.groupByKey()

    //对同一个省份所有广告的集合进行排序并取前3条，排序规则为广告点击总数
    val resRDD = GroupRDD.mapValues {
      x => x.toList.sortWith((x, y) => x._2 > y._2).take(3)
    }
    resRDD.collect().foreach(println)
  }
}
