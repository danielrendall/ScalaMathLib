package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.Compat.box
import uk.co.danielrendall.mathlib.geom2d.Compass.Compass
import uk.co.danielrendall.mathlib.util.Epsilon

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 23-May-2009 11:08:18
 */
case class Point private (rep: Complex) extends XY {

  import Point._

  override def x: Double = rep.x

  override def y: Double = rep.y

  def displace(vec: Vec): Point = Point(rep.add(vec.rep))

  def rotate(angle: Double): Point = Point(rep.rotate(angle))

  def line(other: Point) = Line(this, other)

  def line(vec: Vec) = Line(this, vec)

  override def toString: String = String.format("(%s, %s)", box(rep.x), box(rep.y))

  def distanceTo(other: Point)
                (implicit epsilon: Epsilon): Double = line(other).length

  def squaredDistanceTo(other: Point)
                       (implicit epsilon: Epsilon): Double = line(other).lengthSquared

  def approximateDistanceTo(other: Point): Double = line(other).approximateLength

  def isOrigin: Boolean = ORIGIN == this

  def getOctant(implicit epsilon: Epsilon): Compass = {
    if (isOrigin) return Compass.CENTER
    val angle = rep.arg
    if (angle < OCT1) return Compass.W
    if (angle < OCT2) return Compass.SW
    if (angle < OCT3) return Compass.S
    if (angle < OCT4) return Compass.SE
    if (angle < OCT5) return Compass.E
    if (angle < OCT6) return Compass.NE
    if (angle < OCT7) return Compass.N
    if (angle < OCT8) return Compass.NW
    Compass.W
  }

  def getQuadrant(implicit epsilon: Epsilon): Compass = {
    if (isOrigin) return Compass.CENTER
    if (rep.x == 0.0d) return if (rep.y > 0.0d) Compass.N
    else Compass.S
    else if (rep.y == 0.0d) return if (rep.x > 0.0d) Compass.E
    else Compass.W
    val angle = rep.arg
    if (angle < QUAD1) return Compass.SW
    if (angle < QUAD2) return Compass.SE
    if (angle < QUAD3) return Compass.NE
    Compass.NW
  }

}

object Point {

  def apply(x: Double, y: Double): Point = Point(Complex(x, y))

  val ORIGIN: Point = Point(0.0d, 0.0d)

  private val OCT1 = -7.0d * Math.PI / 8.0d
  private val OCT2 = -5.0d * Math.PI / 8.0d
  private val OCT3 = -3.0d * Math.PI / 8.0d
  private val OCT4 = -1.0d * Math.PI / 8.0d
  private val OCT5 = 1.0d * Math.PI / 8.0d
  private val OCT6 = 3.0d * Math.PI / 8.0d
  private val OCT7 = 5.0d * Math.PI / 8.0d
  private val OCT8 = 7.0d * Math.PI / 8.0d

  private val QUAD1 = -Math.PI / 2.0d
  private val QUAD2 = 0
  private val QUAD3 = Math.PI / 2.0d
  private val QUAD4 = Math.PI


}
