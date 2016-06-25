object ScalaEquality extends App {

  def isEqual(a: Any, b: Any) = a == b

  println(isEqual(421, 421))


  val x = "abcd".substring(2)
  val y = "abcd".substring(2)
  println(x == y)



  val u = new String("a")
  val v = new String("a")
  println(u eq v)
  println(u == v)

}
