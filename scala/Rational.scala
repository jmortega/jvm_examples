class Rational(n: Int, d: Int) extends Ordered[Rational] {

  require(d != 0)

  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  private def gcd(a: Int, b: Int) : Int =
    if (b == 0) a else gcd(b, a % b)

  override def toString = s"$n / $d"

  def +(that: Rational) =
    new Rational(numer * that.denom + that.numer * denom, denom * that.denom)

  def *(that: Rational) =
    new Rational(numer * that.numer, denom * that.denom)

  def compare(that: Rational) =
    (numer * that.denom - that.numer * denom)
}

object Rational {
  implicit def int2Rational(i: Int) = new Rational(i)
}

object TestRational extends App {
  val oneHalf = new Rational(1, 2)
  val twoThirds = new Rational(2, 3)
  println(3 * oneHalf + twoThirds * 4)
  println(oneHalf > twoThirds)
}

