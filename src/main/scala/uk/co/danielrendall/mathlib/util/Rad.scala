package uk.co.danielrendall.mathlib.util

import uk.co.danielrendall.mathlib.geom2d.Complex

class Rad(val angle: Double) extends AnyVal {

  def +(other: Rad): Rad = Rad.apply(angle + other.angle)

  def -(other: Rad): Rad = Rad.apply(angle - other.angle)

  def *(mult: Double): Rad = Rad.apply(angle * mult)

  def /(div: Double): Rad = Rad.apply(angle / div)

  def toComplex: Complex = Complex.modArg(1, angle)
}

object Rad {

  implicit def radToDouble(rad: Rad): Double = rad.angle

  val TWO_PI: Double = 2.0d * Math.PI

  def apply(angle: Double): Rad = new Rad(mod2Pi(angle))

  def mod2Pi(v: Double): Double = if (v >= 0.0d && v < TWO_PI) v else {
    v - (Math.floor(v / TWO_PI) * TWO_PI)
  }

}
