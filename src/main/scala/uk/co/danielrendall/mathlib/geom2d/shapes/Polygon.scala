package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad

import scala.math.{cos, sin}

trait Polygon extends Shape[Polygon] {
  def points: Seq[Point]
}

object Polygon extends PolygonGen {

  def angleSubtendedBySide(numSides: Int): Rad = Rad(Rad.TWO_PI_AS_DOUBLE / numSides.toDouble)

  sealed trait InitialOrientation
  class InitialPointAtAngle(val angle: Rad) extends InitialOrientation
  case object PointOnPositiveXAxis extends InitialPointAtAngle(Rad.ZERO)
  case object PointOnPositiveYAxis extends InitialPointAtAngle(Rad.PI_BY_2)
  case object PointOnNegativeXAxis extends InitialPointAtAngle(Rad.PI)
  case object PointOnNegativeYAxis extends InitialPointAtAngle(Rad.THREE_PI_BY_2)
  sealed trait NeedsNumberOfSides extends InitialOrientation {
    def getAngle(numSides: Int): Rad = initialAngle + angleSubtendedBySide(numSides) / 2

    protected def initialAngle: Rad
  }
  case object VerticalSideCrossesPositiveXAxis extends NeedsNumberOfSides {
    override protected val initialAngle: Rad = Rad.ZERO
  }
  case object HorizontalSideCrossesPositiveYAxis extends NeedsNumberOfSides {
    override protected val initialAngle: Rad = Rad.PI_BY_2
  }
  case object VerticalSideCrossesNegativeXAxis extends NeedsNumberOfSides {
    override protected val initialAngle: Rad = Rad.PI
  }
  case object HorizontalSideCrossesNegativeYAxis extends NeedsNumberOfSides {
    override protected val initialAngle: Rad = Rad.THREE_PI_BY_2
  }

  sealed trait InitialSize {
    def getDistanceToCorner(numSides: Int): Double
  }
  case class DistanceToCorner(d: Double) extends InitialSize {
    override def getDistanceToCorner(numSides: Int): Double = d
  }
  case class DistanceToMidSide(d: Double) extends InitialSize {
    override def getDistanceToCorner(numSides: Int): Double = {
      d / cos((angleSubtendedBySide(numSides) / 2).angle)
    }
  }
  case class SideLength(d: Double) extends InitialSize {
    override def getDistanceToCorner(numSides: Int): Double = {
      d / (2.0 * sin((angleSubtendedBySide(numSides) / 2).angle))
    }
  }

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

  def apply(points: Seq[Point]): Polygon = {
    points.toList match {
      case p1 :: p2 :: p3 :: rest => apply(p1, p2, p3, rest:_*)
      case _ => throw new IllegalArgumentException("Can't make a polygon with fewer than 3 points")
    }
  }

  def apply(numSides: Int,
            centre: Point = Point.ORIGIN,
            size: InitialSize = SideLength(1),
            orientation: InitialOrientation = VerticalSideCrossesPositiveXAxis): Polygon = {
    assert(numSides >= 3, "Can't make a polygon with fewer than 3 sides")
    val distanceToFirstPoint = size.getDistanceToCorner(numSides)
    val initialAngle = orientation match {
      case angle: InitialPointAtAngle => angle.angle
      case sides: NeedsNumberOfSides => sides.getAngle(numSides)
    }
    val subtendedAngle = angleSubtendedBySide(numSides)
    val p1 = Point(distanceToFirstPoint, 0).rotate(initialAngle)
    val mapper: Point => Point = if (centre != Point.ORIGIN) {
      val vec = Vec(Point.ORIGIN, centre)
      (p: Point) => p.displace(vec)
    } else identity
    apply((0 until numSides).map(r => p1.rotate(subtendedAngle * r)).map(mapper))
  }

}
