package com.MuziMin.project_11.function

import com.MuziMin.project_11.bean.CategoryPojo
import org.apache.commons.lang.time.FastDateFormat
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author : 李煌民
 * @date : 2021-03-01 17:02
 *       ${description}
 **/
class WindowResult extends ProcessWindowFunction[Double, CategoryPojo, String, TimeWindow] {
  private val df: FastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")

  override def process(key: String, context: Context, elements: Iterable[Double], out: Collector[CategoryPojo]): Unit = {
    val currentTime = System.currentTimeMillis()
    val dateTime = df.format(currentTime)
    val totalPrice = elements.iterator.next()

    out.collect(CategoryPojo(key, totalPrice, dateTime))
  }
}
