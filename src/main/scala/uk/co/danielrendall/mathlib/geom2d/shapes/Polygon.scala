package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.Point

trait Polygon extends Shape[Polygon]

object Polygon {

  def apply(point: Point, points: Point*): Polygon = new GenericPolygon(point +: points)

}
