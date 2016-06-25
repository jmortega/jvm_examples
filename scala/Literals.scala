object Literals extends App {

  val hex = 0x2A

  val oct = 052

  val long = 42L

  val double = 0.042e3

  val a = 'A'
  val b = '\u0041'

  1.2 + 2.3
  3 - 1
  'a' - 'b'
  2L * 3L
  11 / 4
  11 % 4
  11.0f / 4.0f

  1.0 >= 1.0
  'a' > 'A'

  val toBe = true
  toBe || !toBe

  println("""|This is
             |a multiline string!""")

  println("""|This is
             |a multiline string!""".stripMargin)

  val aa = 10
  println( s"String ${aa + 9}")
}
