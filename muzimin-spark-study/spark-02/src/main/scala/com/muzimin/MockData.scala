package com.muzimin


import com.muzimin.pojo.{ProductInfo, UserInfo, UserVisitAction}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.util.{Properties, UUID}
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * @author: 李煌民
 * @date: 2024-06-04 14:00
 *        ${description}
 * */
object MockData {
  /**
   * 模拟用户行为信息
   *
   * @return 将用户行为信息组成一行放到数组中
   */
  private def mockUserVisitActionData() = {
    val searchKeywords = Array("火锅", "蛋糕", "重庆辣子鸡", "重庆小面", "呷哺呷哺", "新辣道鱼火锅", "国贸大厦", "太古商场", "日本料理", "温泉")

    //yyyy-MM-dd
    val date = "2024-06-04"

    //四个行为：点击，搜索，下单，支付
    val actions = Array("search", "click", "order", "pay")

    val random = new Random()
    val rows = ArrayBuffer[UserVisitAction]()

    //一共一百个用户，可重复
    for (i <- 0 to 100) {
      val user_id = random.nextInt(100)

      //每一个用户产生10个session
      for (j <- 0 to 10) {
        //不可变的，全局的，独一无二的128bit长度的标识符，用于标识一个session，体现一次会话产生的sessionId是独一无二的
        val session_id = UUID.randomUUID().toString.replace("-", "")

        // 在yyyy-MM-dd后面添加一个随机的小时时间（0-23）
        val baseActionTime = date + " " + random.nextInt(23)

        //// 每个(userid + sessionid)生成0-100条用户访问数据
        for (k <- 0 to random.nextInt(100)) {
          //访问页面的ID
          val page_id = random.nextInt(100)

          // 在yyyy-MM-dd HH后面添加一个随机的分钟时间和秒时间
          val actionTime = baseActionTime +
            ":" + String.valueOf(random.nextInt(59)) +
            ":" + String.valueOf(random.nextInt(59))

          var searchKeyword: String = null
          var clickCategoryId: Long = -1L
          var clickProductId: Long = -1L
          var orderCategoryIds: String = null
          var orderProductIds: String = null
          var payCategoryIds: String = null
          var payProductIds: String = null
          val cityid = random.nextInt(10).toLong

          // 随机确定用户在当前session中的行为
          val action = actions(random.nextInt(4))

          // 根据随机产生的用户行为action决定对应字段的值
          action match {
            case "search" =>
              searchKeyword = searchKeywords(random.nextInt(10))
            case "click" =>
              clickCategoryId = random.nextInt(100).toLong
              clickProductId = String.valueOf(random.nextInt(100)).toLong
            case "order" =>
              orderCategoryIds = random.nextInt(100).toString
              orderProductIds = random.nextInt(100).toString
            case "pay" =>
              payCategoryIds = random.nextInt(100).toString
              payProductIds = random.nextInt(100).toString
          }

          //将一行的数据进行整合
          val visitAction = UserVisitAction(
            date, user_id, session_id,
            page_id, actionTime, searchKeyword,
            clickCategoryId, clickProductId,
            orderCategoryIds, orderProductIds,
            payCategoryIds, payProductIds, cityid)
          rows += visitAction
        }
      }
    }
    rows.toArray
  }

  /**
   * 模拟用户信息表
   */
  private def mockUserInfo() = {
    val rows = ArrayBuffer[UserInfo]()
    val sexes = Array("male", "female")
    val random = new Random()

    // 随机产生100个用户的个人信息
    for (i <- 1 to 100) {
      val user_id = i
      val username = "user" + i
      val name = "name" + i
      val age = random.nextInt(60)
      val professional = "professional" + random.nextInt(100)
      val city = "city" + random.nextInt(100)
      val sex = sexes(random.nextInt(2))
      val nameInfo = UserInfo(user_id, username, name, age, professional, city, sex)
      rows += nameInfo
    }
    rows.toArray
  }

  /**
   * 模拟产品数据表
   */
  private def mockProductInfo() = {
    val rows = ArrayBuffer[ProductInfo]()
    val random = new Random()
    val productStatus = Array(0, 1)

    //随机产生100个随机产品信息
    for (i <- 0 to 100) {
      val product_id = i;
      val productName = "product" + i
      val extendInfo = "{\"product_status\":" + productStatus(random.nextInt(2)) + "}"

      val productInfo = ProductInfo(product_id, productName, extendInfo)
      rows += productInfo
    }
    rows.toArray
  }

  private def insertStarRocks(spark: SparkSession, tableName: String, DF: DataFrame) = {
    val startTime = System.currentTimeMillis()

    DF
      .write.format("starrocks")
      .option("starrocks.fe.http.url", "")
      .option("starrocks.fe.jdbc.url", "")
      .option("starrocks.table.identifier", "dim.user_visit_action")
      .option("starrocks.user", "root")
      .option("starrocks.password", "")
      .mode("append")
      .save()

    val endTime = System.currentTimeMillis()

    val l = (endTime - startTime) / 1000
    println(s"${tableName}执行时间：${l}S")
  }

  //表的表名
  val USER_VISIT_ACTION_TABLE = "user_visit_action"
  val USER_INFO_TABLE = "user_info"
  val PRODUCT_INFO_TABLE = "product_info"

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MockData")

    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    //模拟数据
    val userVisitActionData: Array[UserVisitAction] = this.mockUserVisitActionData()
    println("模拟userVisitActionData数据完成")
    //val userInfoData: Array[UserInfo] = this.mockUserInfo()
    //println("模拟userInfoData数据完成")
    //val productInfo: Array[ProductInfo] = this.mockProductInfo()
    //println("模拟productInfo数据完成")

    //将模拟数据封装成RDD
    val userVisitActionRDD = spark.sparkContext.makeRDD(userVisitActionData)
    //val userInfoRDD = spark.sparkContext.makeRDD(userInfoData)
    //val productInfoRDD = spark.sparkContext.makeRDD(productInfo)

    val userVisitActionDF = userVisitActionRDD.toDF().cache()
    println(s"开始userVisitActionDF插入, 条数为：${userVisitActionDF.count()}")
    insertStarRocks(spark, USER_VISIT_ACTION_TABLE, userVisitActionDF)

    //val userInfoDF = userInfoRDD.toDF().cache()
    //println(s"开始userInfoDF插入, 条数为：${userInfoDF.count()}")
    //insertStarRocks(spark, USER_INFO_TABLE, userInfoDF)

    //val productInfoDF = productInfoRDD.toDF().cache()
    //println(s"开始productInfoDF插入, 条数为：${productInfoDF.count()}")
    //insertStarRocks(spark, PRODUCT_INFO_TABLE, productInfoDF)

    spark.stop()
  }
}
