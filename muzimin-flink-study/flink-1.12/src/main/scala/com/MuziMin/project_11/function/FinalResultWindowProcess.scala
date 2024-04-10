package com.MuziMin.project_11.function

import java.util.stream.Collectors
import java.util.{Comparator, PriorityQueue}

import com.MuziMin.project_11.bean.CategoryPojo
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.JavaConverters._

/**
 * @author : 李煌民
 * @date : 2021-03-01 17:33
 *
 **/
class FinalResultWindowProcess() extends ProcessWindowFunction[CategoryPojo, Any, String, TimeWindow] {

  override def process(key: String, context: Context, elements: Iterable[CategoryPojo], out: Collector[Any]): Unit = {
    //实时计算出当天零点截止到当前时间的销售总额 11月11日 00:00:00 ~ 23:59:59
    var total = 0D //用于记录销售总额

    //计算出各个分类的销售top3:如: "女装": 10000 "男装": 9000 "图书":8000
    val queue = new PriorityQueue[CategoryPojo](3, new Comparator[CategoryPojo] {
      override def compare(o1: CategoryPojo, o2: CategoryPojo): Int = {
        if (o1.totalPrice >= o2.totalPrice) {
          1
        } else {
          -1
        }
      }
    })

    elements.foreach(
      iter => {
        val price = iter.totalPrice
        total += price

        if (queue.size() < 3) {
          queue.add(iter)
        } else {
          if (price >= queue.peek().totalPrice) {
            queue.poll()
            queue.add(iter)
          }
        }
      }
    )

    val top3List = queue
      .stream()
      .sorted(new Comparator[CategoryPojo] {
        override def compare(o1: CategoryPojo, o2: CategoryPojo): Int = {
          if (o1.totalPrice >= o2.totalPrice) {
            -1
          } else {
            1
          }
        }
      }).collect(Collectors.toList[CategoryPojo])
      .asScala.toList
      .map(
        item => {
          s"分类：${item.category}===金额：${item.totalPrice}"
        }
      )


    val result = total.formatted("%.2f")

    println(s"时间：$key === 总金额：$result")

    for (i <- 0 until top3List.size) {
      println(s"top$i:${top3List(i)}")
    }
  }
}
