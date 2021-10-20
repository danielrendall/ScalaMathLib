package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad

/**
 * A generic polygon for when there isn't a specific class
 *
 */
class GenericPolygon(val points: Seq[Point]) extends Polygon with Shape[Polygon] with PolygonHelper[Polygon] {

  override def rotate(angle: Rad, about: Point): Polygon =
    new GenericPolygon(points.map(_.rotate(angle, about)))

  override def translate(vec: Vec): Polygon =
    new GenericPolygon(points.map(_.displace(vec)))

}
