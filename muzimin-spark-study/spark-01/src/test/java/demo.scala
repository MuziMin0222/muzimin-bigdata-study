import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author : 李煌民
 * @date : 2021-03-01 11:21
 *       ${description}
 **/
object demo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("demo")
    val sc = new SparkContext(conf)

    val list = List(1, 2, 3, 4)
    val rdd = sc.parallelize(list)

    println(rdd.count() == list.length)
  }
}
