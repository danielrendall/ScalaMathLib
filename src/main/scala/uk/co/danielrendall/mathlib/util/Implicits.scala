package uk.co.danielrendall.mathlib.util

import uk.co.danielrendall.mathlib.geom2d.Point

object Implicits {

  implicit class DoubleOps(double: Double) {

    def ~==(other: Double)
           (implicit epsilon: Epsilon): Boolean = (double == other) || (Math.abs(double - other) < epsilon.value)
  }

  implicit class RadOps(rad: Rad) {
    def ~==(other: Rad)
           (implicit epsilon: Epsilon): Boolean = rad.angle ~== other.angle
  }

  implicit class PointOps(point: Point) {
    def ~==(other: Point)
           (implicit epsilon: Epsilon): Boolean = (point.x ~== other.x) && (point.y ~== other.y)
  }

}
