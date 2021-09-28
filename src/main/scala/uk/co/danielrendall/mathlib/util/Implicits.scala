package uk.co.danielrendall.mathlib.util

object Implicits {

  implicit class DoubleOps(double: Double) {

    def ~==(other: Double)
           (implicit epsilon: Epsilon): Boolean = (double == other) || (Math.abs(double - other) < epsilon.value)


  }

  implicit class RadOps(rad: Rad) {
    def ~==(other: Rad)
           (implicit epsilon: Epsilon): Boolean = rad.angle ~== other.angle
  }

}
