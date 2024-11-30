package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.Compat.box
import uk.co.danielrendall.mathlib.util.{Epsilon, Mathlib, Rad}

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 23-May-2009 10:51:28
 */
case class Vec private (rep: Complex) extends XY {

  override def x: Double = rep.x

  override def y: Double = rep.y

  def length(implicit epsilon: Epsilon): Double = rep.mod

  def lengthSquared(implicit epsilon: Epsilon): Double = rep.modSquared

  def approximateLength: Double = {
    val absX = Math.abs(x)
    val absY = Math.abs(y)
    val sum = absX + absY
    if (sum == 0.0d) 0.0d
    else {
      val difference = Math.abs(absX - absY)
      val fractionalDifference = difference / sum
      //fd = 0 = no difference = multiply sum by 1/sqrt(2)
      //fd = 1 = difference = sum = multiply sum by 1
      val factor = (1 - Mathlib.SQRT_TWO_RECIPROCAL) * fractionalDifference + Mathlib.SQRT_TWO_RECIPROCAL
      sum * factor
    }
  }

  def angle(implicit epsilon: Epsilon): Rad = rep.arg

  def add(other: Vec) = Vec(rep.add(other.rep))

  def sub(other: Vec) = Vec(rep.sub(other.rep))

  def neg = Vec(rep.neg)

  def rotate(angle: Rad) = Vec(rep.rotate(angle))

  def scale(factor: Double) = Vec(rep.times(factor))

  def shrink(factor: Double): Vec = {
    if (factor == 0.0d) throw new IllegalArgumentException("Can't shrink by factor of zero")
    scale(1.0d / factor)
  }

  def scaleAndRotate(factor: Double, angle: Rad) = Vec(rep.times(Complex.modArg(factor, angle)))

  def shrinkAndRotate(factor: Double, angle: Rad): Vec = scaleAndRotate(1.0d / factor, angle)

  override def toString: String = toString(5, 3)

  def toString(whole: Int, decimal: Int): String = {
    val format = "%" + whole + "." + decimal + "f"
    String.format("(" + format + ", " + format + ")", box(rep.x), box(rep.y))
  }

  def normalize(implicit epsilon: Epsilon) = Vec(Complex.unit(rep.arg))

  def dotProduct(other: Vec): Double = x * other.x + y * other.y

}

object Vec {

  def apply(x: Double, y: Double): Vec = Vec(Complex(x, y))

  def apply(to: Point): Vec = Vec(to.rep)

  def apply(from: Point, to: Point): Vec = Vec(to.rep.sub(from.rep))

  def modArg(mod: Double, arg: Rad): Vec = Vec(Complex.modArg(mod, arg))

  val ZERO: Vec = Vec(0.0d, 0.0d)

  val I: Vec = Vec(1.0d, 0.0d)
  val J: Vec = Vec(0.0d, 1.0d)

}
