package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.Compat.box
import uk.co.danielrendall.mathlib.geom2d.Compass.Compass
import uk.co.danielrendall.mathlib.util.{Epsilon, Rad}

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 23-May-2009 11:08:18
 */
case class Point private (rep: Complex) extends XY {

  import Point._

  override def x: Double = rep.x

  override def y: Double = rep.y

  def displace(vec: Vec): Point = Point(rep.add(vec.rep))

  def rotate(angle: Rad): Point = Point(rep.rotate(angle))

  def rotate(angle: Rad, about: Point): Point = Point(rep.rotate(angle, about.rep))

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
    if (angle < OCT1) return Compass.E
    if (angle < OCT2) return Compass.NE
    if (angle < OCT3) return Compass.N
    if (angle < OCT4) return Compass.NW
    if (angle < OCT5) return Compass.W
    if (angle < OCT6) return Compass.SW
    if (angle < OCT7) return Compass.S
    if (angle < OCT8) return Compass.SE
    Compass.E
  }

  def getQuadrant(implicit epsilon: Epsilon): Compass = {
    if (isOrigin) return Compass.CENTER
    if (rep.x == 0.0d) return if (rep.y > 0.0d) Compass.N
    else Compass.S
    else if (rep.y == 0.0d) return if (rep.x > 0.0d) Compass.E
    else Compass.W
    val angle = rep.arg
    if (angle < Rad.PI_BY_2) return Compass.NE
    if (angle < Rad.PI) return Compass.NW
    if (angle < Rad.THREE_PI_BY_2) return Compass.SW
    Compass.SE
  }

}

object Point {

  def apply(x: Double, y: Double): Point = Point(Complex(x, y))

  val ORIGIN: Point = Point(0.0d, 0.0d)

  private val OCT1: Rad = Rad(1.0d * Math.PI / 8.0d)
  private val OCT2: Rad = Rad(3.0d * Math.PI / 8.0d)
  private val OCT3: Rad = Rad(5.0d * Math.PI / 8.0d)
  private val OCT4: Rad = Rad(7.0d * Math.PI / 8.0d)
  private val OCT5: Rad = Rad(9.0d * Math.PI / 8.0d)
  private val OCT6: Rad = Rad(11.0d * Math.PI / 8.0d)
  private val OCT7: Rad = Rad(13.0d * Math.PI / 8.0d)
  private val OCT8: Rad = Rad(15.0d * Math.PI / 8.0d)

}
