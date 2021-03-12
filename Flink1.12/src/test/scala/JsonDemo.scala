import com.MuziMin.source_02.bean.Order
import com.alibaba.fastjson.JSON

/**
 * @author : 李煌民
 * @date : 2021-02-26 14:24
 *       ${description}
 **/
object JsonDemo {

  def main(args: Array[String]): Unit = {
    val order = Order("11", 12, 31, 4134)

    println(JSON.toJSONString(order, true))
  }
}
