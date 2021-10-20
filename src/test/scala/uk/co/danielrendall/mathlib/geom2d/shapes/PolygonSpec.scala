package uk.co.danielrendall.mathlib.geom2d.shapes

import org.specs2.mutable.Specification
import uk.co.danielrendall.mathlib.ApproxMatchers
import uk.co.danielrendall.mathlib.geom2d.Point
import uk.co.danielrendall.mathlib.util.Rad
import uk.co.danielrendall.mathlib.util.epsilon.Default

class PolygonSpec extends Specification with ApproxMatchers {

  "Polygon.getPerimeter" should {

    val aSquare = Polygon(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0), Point(0.0, 1.0))

    val aTriangle = Polygon(Point.ORIGIN, Point(3.0, 0.0), Point(0.0, 4.0))

    "Work for a square" in {
      aSquare.perimeter must be_~==(4.0)
    }

    "Work for a rotated square" in {
      aSquare.rotate(Rad.PI_BY_3).perimeter must be_~==(4.0)
    }

    "Work for a triangle" in {
      aTriangle.perimeter must be_~==(12.0)
    }

    "Work for a rotated triangle" in {
      aTriangle.rotate(Rad.PI_BY_8).perimeter must be_~==(12.0)
    }

  }

  "Polygon.evaluate" should {
    val aSquare = Polygon(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0), Point(0.0, 1.0))

    "Work for a square" in {
      aSquare.evaluate(0.0) must be_~==(Point.ORIGIN)
      aSquare.evaluate(0.125) must be_~==(Point(0.5, 0.0))
      aSquare.evaluate(0.25) must be_~==(Point(1.0, 0.0))
      aSquare.evaluate(0.375) must be_~==(Point(1.0, 0.5))
      aSquare.evaluate(0.5) must be_~==(Point(1.0, 1.0))
      aSquare.evaluate(0.625) must be_~==(Point(0.5, 1.0))
      aSquare.evaluate(0.75) must be_~==(Point(0.0, 1.0))
      aSquare.evaluate(0.875) must be_~==(Point(0.0, 0.5))
      aSquare.evaluate(1.0) must be_~==(Point.ORIGIN)
    }
  }

  "Polygon.apply" should {
    "Create a triangle" in {
      Polygon(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0)) must_=== Triangle(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0))
    }
    "Create a hexagon" in {
      Polygon(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0), Point(0.5, 0.5), Point(0, 1.0), Point(-0.5, 0.5)) must_===
        Hexagon(Point.ORIGIN, Point(1.0, 0.0), Point(1.0, 1.0), Point(0.5, 0.5), Point(0, 1.0), Point(-0.5, 0.5))
    }
  }

}
