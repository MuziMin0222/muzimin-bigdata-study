package itcast.TableApi

/**
 * @author : 李煌民
 * @date : 2020-12-30 09:59
 *       ${description}
 **/
case class CaseClass1(a: String, b: String, c: String, rowtime: Long) {
  override def toString: String = {
    s"""a:$a,b:$b,c:$c,rowtime:$rowtime"""
  }
}
