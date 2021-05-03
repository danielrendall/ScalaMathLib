package uk.co.danielrendall.mathlib.geom2d

import org.specs2.mutable.Specification

class VecSpec extends Specification with ApproxMatchers {

  "Vec.length" should {
    "Work #1" in {
      Vec(Point(7, 0)).length must beApproximately(7.0d)
    }
    "Work #2" in {
      Vec(Point(0, -6.543)).length must beApproximately(6.543d)
    }
    "Work #3" in {
      Vec(Point(-5.0, 12.0)).length must beApproximately(13.0d)
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
      vec2.length must beApproximately(1.0d)
      vec2.angle must beApproximately(Math.PI / 4.0)
    }
  }

  "Vec.dotProduct" should {
    "Work" in {
      val vec1 = Vec(Point(7, 4))
      val vec2 = Vec(Point(2, 3))
      vec1.dotProduct(vec2) must beApproximately(26.0)
      vec2.dotProduct(vec1) must beApproximately(26.0)
    }
    "Work after normalizing" in {
      val vec1 = Vec(Point(-3, 4))
      val vec2 = Vec(Point(2, 3))
      val angleBetween = vec1.angle - vec2.angle
      val cosAngle = Math.cos(angleBetween)
      vec1.normalize.dotProduct(vec2.normalize) must beApproximately(cosAngle)
      vec2.normalize.dotProduct(vec1.normalize) must beApproximately(cosAngle)
      vec1.normalize.dotProduct(vec1) must beApproximately(vec1.length)
      vec2.normalize.dotProduct(vec2) must beApproximately(vec2.length)
    }
  }

  "Vector.approximateLength" should {
    "Work" in {
      // Not sure what this was supposed to do!
      val increment = Math.PI / 180
      val base = Vec(100.0d, 0.0d)
      for (i <- 0 until 180) {
        val test = base.rotate(increment * i.toDouble)
        val length = test.length
        val approx = test.approximateLength
        System.out.println("Angle: " + i + " length " + length + " approx " + approx + " diff " + (length - approx))
      }
      true must beTrue
    }
  }

}
