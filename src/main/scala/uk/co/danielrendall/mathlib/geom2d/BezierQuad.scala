package uk.co.danielrendall.mathlib.geom2d

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 23-May-2009 18:20:30
 */
case class BezierQuad private (start: Vec, control: Vec, end: Vec) extends ParametricCurve {

  override def evaluate(parameter: Double): Point = {
    val oneMinus = 1.0d - parameter
    val v0 = start.scale(oneMinus * oneMinus)
    val v1 = control.scale(2.0d * oneMinus * parameter)
    val v2 = end.scale(parameter * parameter)
    Point.ORIGIN.displace(v0.add(v1).add(v2))
  }

  override def getBoundingBox: BoundingBox = BoundingBox.containing(Array[Double](start.x, control.x, end.x), Array[Double](start.y, control.y, end.y))

  override def toString: String = String.format("BezierQuad { %s , %s , %s }", start, control, end)

}

object BezierQuad {
  def apply(start: Point, control: Point, end: Point): BezierQuad =
    BezierQuad(Vec(start), Vec(control), Vec(end))
}
