package uk.co.danielrendall.mathlib.geom2d

import org.specs2.mutable.Specification
import uk.co.danielrendall.mathlib.UsesDefaultEpsilon

class PointSpec extends Specification
  with UsesDefaultEpsilon {


  "Point.getQuadrant" should {
    "Work for NW" in {
      Point(-3.0d, 4.0d).getQuadrant must_=== Compass.NW
    }
    "Work for W" in {
      Point(-3.0d, 0.0d).getQuadrant must_=== Compass.W
    }
    "Word for NE" in {
      Point(2.0d, 5.0d).getQuadrant must_=== Compass.NE
    }
    "Work for CENTER" in {
      Point(0.0d, 0.0d).getQuadrant must_=== Compass.CENTER
    }
    "Work for S" in {
      Point(0.0d, -6.0d).getQuadrant must_=== Compass.S
    }
    "Work for N" in {
      Point(0.0d, 7.5d).getQuadrant must_=== Compass.N
    }
  }

  "Point.getOctant" should {
    "Work for NW" in {
      Point(-3.0d, 4.0d).getOctant must_=== Compass.NW
    }
    "Work for W #1" in {
      Point(-30.0d, 1.0d).getOctant must_=== Compass.W
    }
    "Work for W #2" in {
      Point(-30.0d, -1.0d).getOctant must_=== Compass.W
    }
    "Work for NE" in {
      Point(5.0d, 5.0d).getOctant must_=== Compass.NE
    }
    "Work for CENTER" in {
      Point(0.0d, 0.0d).getOctant must_=== Compass.CENTER
    }
    "Work for S" in {
      Point(0.5d, -6.0d).getOctant must_=== Compass.S
    }
    "Work for N" in {
      Point(-0.3d, 7.5d).getOctant must_=== Compass.N
    }
  }

}
