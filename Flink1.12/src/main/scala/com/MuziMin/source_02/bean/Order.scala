package com.MuziMin.source_02.bean

/**
 * @author : 李煌民
 * @date : 2021-02-25 15:01
 *       ${description}
 **/
case class Order(
                  id: String,
                  userId: Int,
                  money: Int,
                  createTime: Long
                ) {
  override def toString: String = {
    s"""{"id":"$id","userId":"$userId","money":"$money","createTime","$createTime"}"""
  }
}
