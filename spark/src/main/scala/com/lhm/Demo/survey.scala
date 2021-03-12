package com.lhm.Demo

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author : 李煌民
 * @date : 2020-10-27 18:02
 *       ${description}
 **/
object survey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("SparkWithKafka")
    val sc = new SparkContext(conf)

    val sourceRDD = sc.textFile("D:\\survey.txt")
    val mapRDD = sourceRDD.map(
      line => {
        val arrs = line.split("、")
        var str1 = arrs(1).replaceAll("：", ":").substring(0, arrs(1).length - 1)
        val arr1 = str1.split(":")
        str1 = "\"[{\"\"question\"\":\"\"1. " + arr1(0) + "\"\",\"\"answer\"\":\"\"" + arr1(1) + "\"\"}"

        var str2 = arrs(2).replaceAll("：", ":").substring(0, arrs(2).length - 1)
        val arr2 = str2.split(":")
        str2 = "{\"\"question\"\":\"\"2. " + arr2(0) + "\"\",\"\"answer\"\":\"\"" + arr2(1) + "\"\"}"

        var str3 = arrs(3).replaceAll("：", ":").substring(0, arrs(3).length - 1)
        val arr3 = str3.split(":")
        str3 = "{\"\"question\"\":\"\"3. " + arr3(0) + "\"\",\"\"answer\"\":\"\"" + arr3(1) + "\"\"}"

        var str4 = arrs(4).replaceAll("：", ":").substring(0, arrs(4).length - 1)
        val arr4 = str4.split(":")
        str4 = "{\"\"question\"\":\"\"4. " + arr4(0) + "\"\",\"\"answer\"\":\"\"" + arr4(1) + "\"\"}"

        var str5 = arrs(5).replaceAll("：", ":").substring(0, arrs(5).length - 1)
        val arr5 = str5.split(":")
        str5 = "{\"\"question\"\":\"\"5. " + arr5(0) + "\"\",\"\"answer\"\":\"\"" + arr5(1) + "\"\"}"

        var str6 = arrs(6).replaceAll("：", ":")
        val arr6 = str6.split(":")
        str6 = "{\"\"question\"\":\"\"6. " + arr6(0) + "\"\",\"\"answer\"\":\"\"" + arr6(1) + "\"\"}]\""

        str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6
      }
    )
    mapRDD.foreach(println(_))
    mapRDD.saveAsTextFile("D:\\surver_res.txt")
  }
}
