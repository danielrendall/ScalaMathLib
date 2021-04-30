package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.geom2d.Complex.EPSILON
import uk.co.danielrendall.mathlib.util.Mathlib

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @created 23-May-2009 10:05:23
 */
final case class Complex(x: Double, y: Double) extends XY {

  private val xIsZero = Math.abs(x) < EPSILON
  private val yIsZero = Math.abs(y) < EPSILON

  def neg = new Complex(0.0d - x, 0.0d - y)

  def add(other: Complex) = Complex(x + other.x, y + other.y)

  def sub(other: Complex): Complex = add(other.neg)

  def mod: Double = if (xIsZero && yIsZero) 0.0 else Mathlib.pythagorus(x, y)

  def modSquared: Double = if (xIsZero && yIsZero) 0.0 else Mathlib.pythagorusSquared(x, y)

  def arg: Double = if (xIsZero && yIsZero) 0.0d else Math.atan2(y, x)

  def times(m: Double) = new Complex(x * m, y * m)

  def times(c: Complex): Complex = {
    // (x + iy)(a+ib) = xa - yb + (xb + ya)i
    Complex(x * c.x - y * c.y, x * c.y + y * c.x)
  }

  def rotate(angle: Double): Complex = times(Complex.unit(angle))

  override def toString: String = String.format("(%s + %si)", x, y)

}

object Complex {
  private val EPSILON = Double.MinValue * 100

  def unit: Complex = unit(1.0d)

  def unit(arg: Double): Complex = modArg(1.0d, arg)

  def modArg(mod: Double, arg: Double) = new Complex(mod * Math.cos(arg), mod * Math.sin(arg))


}
