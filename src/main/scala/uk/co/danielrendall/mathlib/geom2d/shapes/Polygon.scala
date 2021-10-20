package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad

case class Polygon(points: Seq[Point]) extends Shape[Polygon] with PolygonHelper[Polygon] {

  assert(points.size > 2, s"Not enough points: ${points.mkString(", ")}")

  override def rotate(angle: Rad, about: Point): Polygon =
    copy(points = points.map(_.rotate(angle, about)))

  override def translate(vec: Vec): Polygon =
    copy(points = points.map(_.displace(vec)))

}

object Polygon {

  def apply(point: Point, points: Point*): Polygon = Polygon(point +: points)

}
