package uk.co.danielrendall.mathlib.util

import uk.co.danielrendall.mathlib.geom2d.Complex

class Rad(val angle: Double) extends AnyVal {

  def +(other: Rad): Rad = Rad.apply(angle + other.angle)

  def -(other: Rad): Rad = Rad.apply(angle - other.angle)

  def *[T](mult: T)(implicit n: Numeric[T]): Rad = Rad.apply(angle * n.toDouble(mult))

  def /[T](div: T)(implicit n: Numeric[T]): Rad = Rad.apply(angle / n.toDouble(div))

  def toComplex: Complex = Complex.modArg(1, angle)

  override def toString: String = angle + "rad"
}

object Rad {

  implicit def radToDouble(rad: Rad): Double = rad.angle

  val TWO_PI_AS_DOUBLE: Double = Math.PI * 2.0

  val PI: Rad = Rad(Math.PI)

  val TWO_PI: Rad = PI * 2.0

  val PI_BY_8: Rad = PI / 8.0

  val PI_BY_4: Rad = PI / 4.0

  val PI_BY_3: Rad = PI / 3.0

  val PI_BY_2: Rad = PI / 2.0

  val TWO_PI_BY_3: Rad = TWO_PI / 3.0

  val THREE_PI_BY_2: Rad = PI * 3.0 / 2.0

  val ZERO: Rad = Rad(0.0d)

  def apply(angle: Double): Rad = new Rad(mod2Pi(angle))

  def mod2Pi(v: Double): Double = if (v >= 0.0d && v < TWO_PI_AS_DOUBLE) v else {
    v - (Math.floor(v / TWO_PI_AS_DOUBLE) * TWO_PI_AS_DOUBLE)
  }

}
