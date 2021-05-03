package uk.co.danielrendall.mathlib.geom2d

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @created 23-May-2009 11:07:08
 */
case class Line(start: Point, end: Point) extends ParametricCurve {

  lazy val vec: Vec = Vec(start, end)

  def length: Double = vec.length

  def lengthSquared: Double = vec.lengthSquared

  def approximateLength: Double = vec.approximateLength

  override def evaluate(parameter: Double): Point = start.displace(vec.scale(parameter))

  override def getBoundingBox: BoundingBox = BoundingBox.containing(Array[Double](start.x, end.x), Array[Double](start.y, end.y))

  override def toString: String = String.format("Line { %s , %s }", start, end)

}

object Line {

  def apply(start: Point, vec: Vec): Line = Line(start, start.displace(vec))

}
