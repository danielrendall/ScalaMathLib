package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.Compat.box
import uk.co.danielrendall.mathlib.util.{Epsilon, Mathlib}

import scala.annotation.tailrec

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 27-Jun-2009 16:41:14
 */
case class BoundingBox private (minX: Double, maxX: Double, minY: Double, maxY: Double) {

  lazy val center: Point = Point(Mathlib.mean(minX, maxX), Mathlib.mean(minY, maxY))

  def getWidth: Double = maxX - minX

  def getHeight: Double = maxY - minY

  def contains(other: BoundingBox): Boolean = minX < other.minX && maxX > other.maxX && minY < other.minY && maxY > other.maxY

  def isContainedBy(other: BoundingBox): Boolean = other.contains(this)

  def isOutside(other: BoundingBox): Boolean = minX > other.maxX || maxX < other.minX || minY > other.maxY || maxY < other.minY

  def intersects(other: BoundingBox): Boolean = !contains(other) && !isContainedBy(other) && !isOutside(other)

  def randomPoint: Point = {
    val x = Math.random * getWidth + minX
    val y = Math.random * getHeight + minY
    Point(x, y)
  }

  def centerDistanceTo(other: BoundingBox)
                      (implicit epsilon: Epsilon): Double = center.distanceTo(other.center)

  def squaredCenterDistanceTo(other: BoundingBox)
                             (implicit epsilon: Epsilon): Double = center.squaredDistanceTo(other.center)

  def forSvg: String = String.format("%5.1f %5.1f %5.1f %5.1f", box(minX), box(minY), box(maxX), box(maxY))

  def expandToInclude(other: BoundingBox): BoundingBox = {
    if (contains(other)) return this
    if (isContainedBy(other)) return other
    val newMinX = if (other.minX < minX) other.minX else minX
    val newMaxX = if (other.maxX > maxX) other.maxX else maxX
    val newMinY = if (other.minY < minY) other.minY else minY
    val newMaxY = if (other.maxY > maxY) other.maxY else maxY
    new BoundingBox(newMinX, newMaxX, newMinY, newMaxY)
  }

  override def toString: String = String.format("{minX=%5.4f, maxX=%5.4f, minY=%5.4f, maxY=%5.4f, width=%5.4f, height=%5.4f'}'",
    box(minX), box(maxX), box(minY), box(maxY), box(getWidth), box(getHeight))

}

object BoundingBox {

  def empty = new BoundingBox(Double.MaxValue, -Double.MaxValue, Double.MaxValue, -Double.MaxValue)

  def containing(points: Point*): BoundingBox = {
    @tailrec
    def recurse(remaining: Seq[Point], minX: Double, maxX: Double, minY: Double, maxY: Double): BoundingBox =
      remaining.headOption match {
        case Some(p) =>
          recurse(remaining.tail, Math.min(minX, p.x), Math.max(maxX, p.x), Math.min(minY, p.y), Math.max(maxY, p.y))
        case None => BoundingBox(minX, maxX, minY, maxY)
      }

    recurse(points, Double.MaxValue, -Double.MaxValue, Double.MaxValue, -Double.MaxValue)
  }

  def containing(xValues: Array[Double], yValues: Array[Double]): BoundingBox = {
    @tailrec
    def recurse(remaining: Seq[Double], min: Double, max: Double): (Double, Double) = {
      remaining.headOption match {
        case Some(d) =>
          recurse(remaining.tail, Math.min(min, d), Math.max(max, d))
        case None => (min, max)
      }
    }
    val (minX, maxX) = recurse(xValues, Double.MaxValue, -Double.MaxValue)
    val (minY, maxY) = recurse(yValues, Double.MaxValue, -Double.MaxValue)
    BoundingBox(minX, maxX, minY, maxY)
  }

}
