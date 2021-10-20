package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.Point

trait Polygon extends Shape[Polygon]

object Polygon {

  def apply(p1: Point, p2: Point, rest: Point*): Polygon = ???

}
