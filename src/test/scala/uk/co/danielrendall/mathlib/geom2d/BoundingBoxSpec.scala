package uk.co.danielrendall.mathlib.geom2d

import org.specs2.mutable.Specification

import java.lang.{Double => JDouble}
import java.util.Comparator

class BoundingBoxSpec extends Specification {

  "BoundingBox.containing" should {
    "Work #1" in {
      val bb1 = BoundingBox.containing(
        Array[Double](10.1d, 0.02d, 5.3d, 5.5d),
        Array[Double](-3.0d, 5.23d, 1.2d, -10.4d))
      bb1 must_=== BoundingBox(0.02d, 10.1d, -10.4d, 5.23d)
    }
    "Work #2" in {
      val bb2 = BoundingBox.containing(
        Array[Double](-10.1d, -0.02d, -5.3d, -5.5d),
        Array[Double](-3.0d, -5.23d, -1.2d, -10.4d))
      bb2 must_=== BoundingBox(-10.1d, -0.02d, -10.4d, -1.2d)
    }
    "Work #3" in {
      val bb3 = BoundingBox.containing(
        Array[Double](-10.0d, -5.0d, 6.0d, 11.0d),
        Array[Double](12.0d, 7.0d, -8.0d, -13.0d))
      bb3 must_=== BoundingBox(-10.0d, 11.0d, -13.0d, 12.0d)
    }
  }

  "BoundingBox.expandToInclude" should {
    val initial = BoundingBox(-7.0d, -3.0d, -2.0d, 4.0d)
    "Work #1" in {
      initial must_=== BoundingBox(-7.0d, -3.0d, -2.0d, 4.0d)
    }
    "Work #2" in {
      initial.expandToInclude(BoundingBox(-6.0d, -2.0d, -1.0d, 3.0d)) must_===
        BoundingBox(-7.0d, -2.0d, -2.0d, 4.0d)
    }
    "Work #3" in {
      initial.expandToInclude(BoundingBox(-8.0d, -2.0d, 5.0d, 7.0d)) must_===
        BoundingBox(-8.0d, -2.0d, -2.0d, 7.0d)
    }
    "Work #4" in {
      initial.expandToInclude(BoundingBox(12.0d, 14.0d, 10.0d, 11.0d)) must_===
        BoundingBox(-7.0d, 14.0d, -2.0d, 11.0d)
    }
  }

  "BoundingBox.contains" should {
    val initial = BoundingBox(-7.0d, -3.0d, -2.0d, 4.0d)
    val inner = BoundingBox(-6.0d, -4.0d, -1.0d, 3.0d)
    val outer = BoundingBox(-10.0d, 0.0d, -4.0d, 6.0d)
    val outsideInitial = BoundingBox(-2.0d, -1.0d, -3.0d, 5.0d)

    "initial must contain inner" in {
      initial.contains(inner) must beTrue
    }
    "outer must contain initial" in {
      outer.contains(initial) must beTrue
    }
    "outer must contain inner" in {
      outer.contains(inner) must beTrue
    }
    "inner does not contain outsideInitial" in {
      inner.contains(outsideInitial) must beFalse
    }
    "initial does not contain outsideInitial" in {
      initial.contains(outsideInitial) must beFalse
    }
    "outer must contain outsideInitial" in {
      outer.contains(outsideInitial) must beTrue
    }
  }

  "BoundingBox.isContainedBy" should {

    val initial = BoundingBox(-7.0d, -3.0d, -2.0d, 4.0d)
    val inner = BoundingBox(-6.0d, -4.0d, -1.0d, 3.0d)
    val outer = BoundingBox(-10.0d, 0.0d, -4.0d, 6.0d)
    val outsideInitial = BoundingBox(-2.0d, -1.0d, -3.0d, 5.0d)

    "inner is contained by initial " in {
      inner.isContainedBy(initial) must beTrue
    }
    "initial is contained by outer" in {
      initial.isContainedBy(outer) must beTrue
    }
    "inner is contained by outer" in {
      inner.isContainedBy(outer) must beTrue
    }
    "outsideInitial is not contained by inner" in {
      outsideInitial.isContainedBy(inner) must beFalse
    }
    "outsideInitial is not contained by initial" in {
      outsideInitial isContainedBy(initial) must beFalse
    }
    "outsideInitial is contained by outer" in {
      outsideInitial.isContainedBy(outer) must beTrue
    }
  }

  "BoundingBox.isOutside" should {
    val initial = BoundingBox(-7.0d, -3.0d, -2.0d, 4.0d)
    val inner = BoundingBox(-6.0d, -4.0d, -1.0d, 3.0d)
    val outer = BoundingBox(-10.0d, 0.0d, -4.0d, 6.0d)
    val outsideInitial = BoundingBox(-2.0d, -1.0d, -3.0d, 5.0d)
    "initial is not outside inner" in {
      initial.isOutside(inner) must beFalse
    }
    "inner is not outside initial" in {
      inner.isOutside(initial) must beFalse
    }
    "outer is not outside initial" in {
      outer.isOutside(initial) must beFalse
    }
    "initial is not outside outer" in {
      initial.isOutside(outer) must beFalse
    }
    "outer is not outside inner" in {
      outer.isOutside(inner) must beFalse
    }
    "inner is not outside outer" in {
      inner.isOutside(outer) must beFalse
    }
    "inner is outside outsideInitial" in {
      inner.isOutside(outsideInitial) must beTrue
    }
    "outsideInitial is outside inner" in {
      outsideInitial.isOutside(inner) must beTrue
    }
    "initial is outside outsideInitial" in {
      initial.isOutside(outsideInitial) must beTrue
    }
    "outsideInitial is outside inner" in {
      outsideInitial.isOutside(initial) must beTrue
    }
    "outer is not outside outsideInitial" in {
      outer.isOutside(outsideInitial) must beFalse
    }
    "outsideInitial is not outside outer" in {
      outsideInitial.isOutside(outer) must beFalse
    }
  }

  "BoundingBox.intersects" should {
    val initial = BoundingBox(-7.0d, -3.0d, -2.0d, 4.0d)
    val intersects = BoundingBox(-8.0d, -4.0d, -1.0d, 3.0d)
    val alsoIntersects = BoundingBox(-3.5d, 2.0d, -4.0d, 7.0d)

    "initial intersects intersects" in {
      initial.intersects(intersects) must beTrue
    }
    "intersects intersects initial" in {
      intersects.intersects(initial) must beTrue
    }
    "initial intersects alsoIntersects" in {
      initial.intersects(alsoIntersects) must beTrue
    }
    "alsoIntersects intersects initisl" in {
      alsoIntersects.intersects(initial) must beTrue
    }
    "intersects does not intersect alsoIntersects" in {
      intersects.intersects(alsoIntersects) must beFalse
    }
    "alsoIntersects does not intersect intersects" in {
      alsoIntersects.intersects(intersects) must beFalse
    }
  }

  "Comparing multiple boxes" should {
    val bigBox = BoundingBox(-100.0d, 100.0d, -100.0d, 100.0d)
    val boxes = Range(0, 1000).map(_ => BoundingBox.containing(bigBox.randomPoint, bigBox.randomPoint))
    "Work for 100000 random boxes" in {
      for {
        i <- boxes.indices
        first = boxes(i)
      } yield {
        bigBox.contains(first) must beTrue
        first.isContainedBy(bigBox) must beTrue
        bigBox.isOutside(first) must beFalse
        first.isOutside(bigBox) must beFalse
        bigBox.intersects(first) must beFalse
        first.intersects(bigBox) must beFalse
        for {
          j <- i + 1 until boxes.size
          second = boxes(j)
        } yield {
          if (second.contains(first)) {
            first.isContainedBy(second) must beTrue
            first.contains(second) must beFalse
            second.isOutside(first) must beFalse
            first.isOutside(second) must beFalse
            second.intersects(first) must beFalse
            first.intersects(second) must beFalse
          }
          else if (first.contains(second)) {
            second.isContainedBy(first) must beTrue
            second.contains(first) must beFalse
            second.isOutside(first) must beFalse
            first.isOutside(second) must beFalse
            second.intersects(first) must beFalse
            first.intersects(second) must beFalse
          }
          else if (first.isOutside(second)) {
            second.isOutside(first) must beTrue
            first.contains(second) must beFalse
            first.isContainedBy(second) must beFalse
            second.contains(first) must beFalse
            second.isContainedBy(second) must beFalse
            second.intersects(first) must beFalse
            first.intersects(second) must beFalse
          }
          else if (first.intersects(second)) {
            second.intersects(first) must beTrue
            first.contains(second) must beFalse
            first.isContainedBy(second) must beFalse
            second.contains(first) must beFalse
            second.isContainedBy(second) must beFalse
            second.isOutside(first) must beFalse
            first.isOutside(second) must beFalse
          }
        }
      }
      true must_=== true
    }
  }

  "BoundingBox.empty" should {
    "Work" in {
      val empty = BoundingBox.empty
      val nonEmpty = BoundingBox(2.0d, 11.0d, 3.0d, 8.0d)
      empty.contains(nonEmpty) must beFalse
      val expanded = empty.expandToInclude(nonEmpty)
      nonEmpty must_=== expanded
    }
  }

}

object BoundingBoxSpec {

  private val comparePythagorus = new Comparator[BoundingBox]() {
    override def compare(o1: BoundingBox, o2: BoundingBox): Int =
      JDouble.compare(o1.center.distanceTo(Point.ORIGIN), o2.center.distanceTo(Point.ORIGIN))
  }

  private val comparePythagorusSquared = new Comparator[BoundingBox]() {
    override def compare(o1: BoundingBox, o2: BoundingBox): Int =
      JDouble.compare(o1.center.squaredDistanceTo(Point.ORIGIN), o2.center.squaredDistanceTo(Point.ORIGIN))
  }

  private val compareApproximate = new Comparator[BoundingBox]() {
    override def compare(o1: BoundingBox, o2: BoundingBox): Int =
      JDouble.compare(o1.center.approximateDistanceTo(Point.ORIGIN), o2.center.approximateDistanceTo(Point.ORIGIN))
  }

}
