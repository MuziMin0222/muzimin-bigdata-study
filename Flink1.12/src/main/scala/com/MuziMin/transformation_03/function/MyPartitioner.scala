package com.MuziMin.transformation_03.function

import org.apache.flink.api.common.functions.Partitioner

/**
 * @author : 李煌民
 * @date : 2021-02-25 17:56
 *       自定义分区
 **/
class MyPartitioner extends Partitioner[Int] {
  //k 是对应的第二个参数
  override def partition(k: Int, i: Int): Int = {
    k % 2
  }
}
