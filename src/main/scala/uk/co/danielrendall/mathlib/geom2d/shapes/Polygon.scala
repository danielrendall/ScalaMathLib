package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.Point

trait Polygon extends Shape[Polygon]

object Polygon extends PolygonGen {

  /**
   * Create a polygon with these points (there must be at least 3). Note that for up to 20 points, this will return a
   * specific case class (e.g. Triangle, Quadrilateral, Pentagon...)
   * @param p1
   * @param p2
   * @param p3
   * @param rest
   * @return
   */
  def apply(p1: Point, p2: Point, p3: Point, rest: Point*): Polygon = create(p1, p2, p3, rest.toList)

}
