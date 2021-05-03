package uk.co.danielrendall.mathlib.geom2d

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 23-May-2009 18:21:39
 */
case class BezierCubic private (start: Vec, control1: Vec, control2: Vec, end: Vec) extends ParametricCurve {

  override def evaluate(parameter: Double): Point = {
    val oneMinus = 1.0d - parameter
    val v0 = start.scale(oneMinus * oneMinus * oneMinus)
    val v1 = control1.scale(3.0d * oneMinus * oneMinus * parameter)
    val v2 = control2.scale(3.0d * oneMinus * parameter * parameter)
    val v3 = end.scale(parameter * parameter * parameter)
    Point.ORIGIN.displace(v0.add(v1).add(v2).add(v3))
  }

  override def getBoundingBox: BoundingBox = BoundingBox.containing(Array[Double](start.x, control1.x, control2.x, end.x), Array[Double](start.y, control1.y, control2.y, end.y))

  override def toString: String = String.format("BezierCubic { %s , %s , %s , %s }", start, control1, control2, end)

}

object BezierCubic {

  def apply(start: Point, control1: Point, control2: Point, end: Point): BezierCubic =
    BezierCubic(Vec(start), Vec(control1), Vec(control2), Vec(end))

}
