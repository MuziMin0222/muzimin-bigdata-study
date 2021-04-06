
/**
 * @author : 李煌民
 * @date : 2021-03-01 12:08
 *       ${description}
 **/
object Demo {
  def main(args: Array[String]): Unit = {
    val str = "\"fdsafa\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\"\u0001\"\""

    println(str.replaceAll("\"\"","").split("\u0001").length)
  }
}
