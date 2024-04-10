package com.lhm.atguigu.Transform

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

//自定义分区测试
object DefinedPartitionerDemo extends {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD_oper").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.makeRDD(List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f")))

    val PartitionRDD = sourceRDD.partitionBy(new MyPartitioner(3))

    PartitionRDD.saveAsTextFile("outData")
  }
}

//声明自定义分区器
class MyPartitioner(partitions:Int) extends Partitioner{
  //返回分区数的个数
  override def numPartitions: Int = {
    partitions
  }

  //让所有的元素都在一号分区
  override def getPartition(key: Any): Int = {
    1
  }
}
