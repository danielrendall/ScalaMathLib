package uk.co.danielrendall.mathlib.geom2d.shapes

import org.specs2.mutable.Specification
import uk.co.danielrendall.mathlib.geom2d.Point

class PolygonSpec extends Specification {


  "Polygon.getPerimeter" should {

    "Work for a square" in {
      Polygon(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0), Point(0.0, 1.0)).perimeter must_=== 4.0
    }

  }

}
