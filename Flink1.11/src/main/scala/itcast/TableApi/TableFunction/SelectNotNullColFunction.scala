package itcast.TableApi.TableFunction

import org.apache.flink.table.functions.ScalarFunction

/**
 * @author : 李煌民
 * @date : 2020-12-09 17:25
 *       ${description}
 **/
class SelectNotNullColFunction extends ScalarFunction {
  def eval(c1: String, c2: String): String = {
    var res = c1
    if (c1 == null || c1 == "") {
      res = c2
    }

    res
  }
}
