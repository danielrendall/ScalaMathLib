package uk.co.danielrendall.mathlib.geom2d

import org.specs2.mutable.Specification
import uk.co.danielrendall.mathlib.{ApproxMatchers, UsesDefaultEpsilon}

class VecSpec extends Specification
  with ApproxMatchers
  with UsesDefaultEpsilon {

  "Vec.length" should {
    "Work #1" in {
      Vec(Point(7, 0)).length must be_~==(7.0d)
    }
    "Work #2" in {
      Vec(Point(0, -6.543)).length must be_~==(6.543d)
    }
    "Work #3" in {
      Vec(Point(-5.0, 12.0)).length must be_~==(13.0d)
    }
  }

  "Vec.add" should {
    "Work" in {
      val vec1 = Vec(Point(7, 0))
      val vec2 = Vec(Point(3, -6))
      vec1.add(vec2) must_=== Vec(Point(10, -6))
    }
  }

  "Vec.normalize" should {
    "Work" in {
      val vec1 = Vec(Point(4, 4))
      val vec2 = vec1.normalize
      vec2.length must be_~==(1.0d)
      vec2.angle must be_~==(Math.PI / 4.0)
    }
  }

  "Vec.dotProduct" should {
    "Work" in {
      val vec1 = Vec(Point(7, 4))
      val vec2 = Vec(Point(2, 3))
      vec1.dotProduct(vec2) must be_~==(26.0)
      vec2.dotProduct(vec1) must be_~==(26.0)
    }
    "Work after normalizing" in {
      val vec1 = Vec(Point(-3, 4))
      val vec2 = Vec(Point(2, 3))
      val angleBetween = vec1.angle - vec2.angle
      val cosAngle = Math.cos(angleBetween)
      vec1.normalize.dotProduct(vec2.normalize) must be_~==(cosAngle)
      vec2.normalize.dotProduct(vec1.normalize) must be_~==(cosAngle)
      vec1.normalize.dotProduct(vec1) must be_~==(vec1.length)
      vec2.normalize.dotProduct(vec2) must be_~==(vec2.length)
    }
  }

}
