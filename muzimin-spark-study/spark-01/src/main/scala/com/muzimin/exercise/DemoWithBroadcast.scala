package com.muzimin.exercise

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object DemoWithBroadcast {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Question3").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val v_idRDD = sc.textFile("data/Test/a.csv").map(line => {
      val strs = line.split(",")
      (strs(1), strs(0)) //(u_id,v_id)
    }).distinct()
    println("v_id:")
    v_idRDD.collect().foreach(line => {
      print(line + "\t")
    })
    println()

    val u_idRDD = sc.textFile("data/Test/b.csv").map(line => {
      val strs: Array[String] = line.split(",")
      (strs(0), (strs(1), strs(2))) //(u_id,(机型,city))
    })
    println("u_id：")
    u_idRDD.collect().foreach(line => {
      print(line + "\t")
    })
    println()

    //使用广播变量优化，将看剧的小的数据进行广播处理
    val v_id_num = v_idRDD
      .distinct()
      .groupBy(_._2)
      .map(t => {
        t._2.map(t1 => {
          (t1, t._2.size)
        })
      })
      .flatMap(t => t) //((u_id1,a_v_id0),4)	((u_id0,a_v_id0),4)	((u_id3,a_v_id0),4)	((u_id2,a_v_id0),4)	((u_id1,a_v_id),2)	((u_id0,a_v_id),2)	((u_id1,a_v_id1),2)	((u_id0,a_v_id1),2)

    val Big_v_idRDD = v_id_num
      .filter(t => {
        t._2 > 2
      })
      .map(x => {
        (x._1._1, x._1._2)
      }) //(u_id1,a_v_id0)
    println("大数据集：")
    Big_v_idRDD.collect().foreach(line => {
      print(line + "\t")
    })
    println()

    val Small_v_id_RDD = v_id_num.filter(t => {
      t._2 <= 2
    }).map(x => {
      (x._1._1, x._1._2)
    }) //(u_id1,a_v_id0)
    println("小数据集：")
    Small_v_id_RDD.collect().foreach(line => {
      print(line + "\t")
    })
    println()

    //大集合和u_idRDD进行join操作
    val BigDate_U_V = u_idRDD.join(Big_v_idRDD).map(line => {
      val city = line._2._1._2
      val xinhao = line._2._1._1
      val v_id = line._2._2
      ((city, xinhao, v_id), 1)
    }).reduceByKey(_ + _) //((shanghai,ios,a_v_id0),1)	((city,Android,a_v_id0),5)..)

    println("电视剧评分高，看的人数多的uv：")
    BigDate_U_V.collect().foreach(line => {
      print(line + "\t")
    })
    println()

    //小集合和u_idRDD进行map操作
    println("电视剧评分低，看的人数少的uv：")
    u_idRDD.join(Small_v_id_RDD).map(line => {
      val city = line._2._1._2
      val xinhao = line._2._1._1
      val v_id = line._2._2
      ((city, xinhao, v_id), 1)
    }).reduceByKey(_ + _).collect().foreach(line => {
      print(line + "\t")
    })
    println()

    //小集合广播处理
    val Small_v_id_RDD_BC: Broadcast[Array[(String, String)]] = sc.broadcast(Small_v_id_RDD.collect())

    println("使用广播变量处理看的人数少的uv：")
    u_idRDD.flatMap(func)
      .filter(t =>{
        t._2._2 != null
      })
      .map(line => {
        val city = line._2._1._2
        val xinhao = line._2._1._1
        val v_id = line._2._2
        ((city, xinhao, v_id), 1)
      })
      .reduceByKey(_ + _)
      .collect()
      .foreach(line => {
      print(line + "\t")
    })
    println()

    def func(tuple: (String, (String, String))) = {
      val list = new ArrayBuffer[(String, ((String, String), String))]()
      for (value <- Small_v_id_RDD_BC.value) {
        if (value._1.equals(tuple._1)) {
          list.append((tuple._1, (tuple._2, value._2)))
        } else {
          list.append((tuple._1, (tuple._2, null)))
        }
      }
      list
    }
  }
}