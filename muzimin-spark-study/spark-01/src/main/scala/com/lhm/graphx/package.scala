package com.lhm

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author : 李煌民
 * @date : 2021-08-05 18:35
 *
 **/
package object graphx {
  val conf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getSimpleName)
  val sc = new SparkContext(conf)
}
