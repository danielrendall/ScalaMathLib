package uk.co.danielrendall.mathlib.geom2d

import uk.co.danielrendall.mathlib.util.Mathlib

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

  def centerDistanceTo(other: BoundingBox): Double = center.distanceTo(other.center)

  def squaredCenterDistanceTo(other: BoundingBox): Double = center.squaredDistanceTo(other.center)

  def forSvg: String = String.format("%5.1f %5.1f %5.1f %5.1f", minX, minY, maxX, maxY)

  def expandToInclude(other: BoundingBox): BoundingBox = {
    if (contains(other)) return this
    if (isContainedBy(other)) return other
    val newMinX = if (other.minX < minX) other.minX else minX
    val newMaxX = if (other.maxX > maxX) other.maxX else maxX
    val newMinY = if (other.minY < minY) other.minY else minY
    val newMaxY = if (other.maxY > maxY) other.maxY else maxY
    new BoundingBox(newMinX, newMaxX, newMinY, newMaxY)
  }

  override def toString: String = String.format("{minX=%5.4f, maxX=%5.4f, minY=%5.4f, maxY=%5.4f, width=%5.4f, height=%5.4f'}'", minX, maxX, minY, maxY, getWidth, getHeight)

}

object BoundingBox {

  def empty = new BoundingBox(Double.MaxValue, -Double.MaxValue, Double.MaxValue, -Double.MaxValue)

  def containing(points: Point*): BoundingBox = {
    var minX = Double.MaxValue
    var maxX = -Double.MaxValue
    var minY = Double.MaxValue
    var maxY = -Double.MaxValue
    for (p <- points) {
      minX = Math.min(minX, p.x)
      maxX = Math.max(maxX, p.x)
      minY = Math.min(minY, p.y)
      maxY = Math.max(maxY, p.y)
    }
    BoundingBox(minX, maxX, minY, maxY)
  }

  def containing(xValues: Array[Double], yValues: Array[Double]): BoundingBox = {
    var minX = Double.MaxValue
    var maxX = -Double.MaxValue
    var minY = Double.MaxValue
    var maxY = -Double.MaxValue
    for (xValue <- xValues) {
      minX = Math.min(minX, xValue)
      maxX = Math.max(maxX, xValue)
    }
    for (yValue <- yValues) {
      minY = Math.min(minY, yValue)
      maxY = Math.max(maxY, yValue)
    }
    BoundingBox(minX, maxX, minY, maxY)
  }

}
