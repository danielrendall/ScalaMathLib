package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{Line, Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad

case class Polygon(points: Seq[Point]) extends Shape[Polygon] with PolygonHelper[Polygon] {

  override def rotate(angle: Rad, about: Point): Polygon =
    copy(points = points.map(_.rotate(angle, about)))

  override def translate(vec: Vec): Polygon =
    copy(points = points.map(_.displace(vec)))

}

object Polygon {

  case class ParamLine(start: Double, end: Double, line: Line) {
    assert(0 <= start && start <= 1.0, s"Start was $start")
    assert(0 <= end && end <= 1.0, s"End was $end")
    assert(start < end)
  }

  def apply(point: Point, points: Point*): Polygon = Polygon(point +: points)

}
