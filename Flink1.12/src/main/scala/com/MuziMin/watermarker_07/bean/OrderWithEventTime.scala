package com.MuziMin.watermarker_07.bean

/**
 * @author : 李煌民
 * @date : 2021-02-26 15:52
 *       ${description}
 **/
case class OrderWithEventTime(
                               orderId: String,
                               userId: Int,
                               money: Int,
                               eventTime: Long
                             )
