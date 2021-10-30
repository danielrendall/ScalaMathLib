package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.Compat.box
import uk.co.danielrendall.mathlib.util.{Epsilon, Mathlib, Rad}
import uk.co.danielrendall.mathlib.util.Implicits.DoubleOps

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @created 23-May-2009 10:05:23
 */
final case class Complex(x: Double, y: Double) extends XY {

  private def xIsZero(implicit epsilon: Epsilon): Boolean = x ~== 0.0d

  private def yIsZero(implicit epsilon: Epsilon) = y ~== 0.0d

  def neg = new Complex(0.0d - x, 0.0d - y)

  def add(other: Complex) = Complex(x + other.x, y + other.y)

  def sub(other: Complex): Complex = add(other.neg)

  def mod(implicit epsilon: Epsilon): Double =
    if (xIsZero && yIsZero) 0.0 else Mathlib.pythagorus(x, y)

  def modSquared(implicit epsilon: Epsilon): Double =
    if (xIsZero && yIsZero) 0.0 else Mathlib.pythagorusSquared(x, y)

  def arg(implicit epsilon: Epsilon): Rad =
    if (xIsZero && yIsZero) Rad.ZERO else Rad(Math.atan2(y, x))

  def times(m: Double) = new Complex(x * m, y * m)

  def times(c: Complex): Complex = {
    // (x + iy)(a+ib) = xa - yb + (xb + ya)i
    Complex(x * c.x - y * c.y, x * c.y + y * c.x)
  }

  def rotate(angle: Rad): Complex = times(angle.toUnitComplex)

  def rotate(angle: Rad, about: Complex): Complex =
    if (about.equals(Complex.zero)) times(angle.toUnitComplex) else {
      sub(about).rotate(angle).add(about)
    }

  override def toString: String = String.format("(%s + %si)", box(x), box(y))

  def ~==(other: Complex)
         (implicit epsilon: Epsilon): Boolean = (x ~== other.x) && (y ~== other.y)

}

object Complex {

  val realUnit: Complex = Complex(1.0, 0.0)

  val imaginaryUnit: Complex = Complex(0.0, 1.0)

  val zero: Complex = Complex(0.0d, 0.0d)

  def unit(arg: Rad): Complex = modArg(1.0d, arg)

  def modArg(mod: Double, arg: Rad) = new Complex(mod * Math.cos(arg), mod * Math.sin(arg))


}
