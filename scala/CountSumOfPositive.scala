object CountSumOfPositive {

  def countSumOfPositive(list: List[Int]) = {   //should be '='
    var sum = 0
    for (number <- list) {
//      val positive = if (number > 0) true
      if (number > 0) {
        sum += number
//        return null
      }
    }
    sum
  }









  def countSumOfPositive1(list: List[Int]) =

    list.foldLeft(0)((acc, el) =>

      acc + (if (el > 0) el else 0))











  def valueIfPos(x: Int) = if (x > 0) x else 0


  def countSumOfPositive2(list: List[Int]) =

    list.foldLeft(0)(_ + valueIfPos(_))








  def test() {
    val d = 2.0.unary_-
    println(d)

    val s1 = "hello"
    val s2 = "he" + "llo"
    println(s1 == s2)
    println(s1 eq s2)
    println(null == null)
    println(null eq null)
  }
}