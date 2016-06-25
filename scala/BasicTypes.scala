class BasicTypes {

  def foo(i: Int) : Int = i

  def baz(a: Array[Int]) = a

  def bar(l: List[Int]) {
    val i = l.head
    val a: Any = i
    val n: Null = null
    Array(1, 2, 3)

    val nothing = throw new Exception()
  }

  def int : Int = if (true) 1 else throw new Exception()

//    new Int
//    new Int {}
}


