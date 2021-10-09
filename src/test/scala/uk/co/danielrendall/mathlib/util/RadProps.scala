package uk.co.danielrendall.mathlib.util

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import uk.co.danielrendall.mathlib.util.Implicits.DoubleOps
import uk.co.danielrendall.mathlib.util.epsilon.Default

class RadProps extends Properties("Rad") {


  property("mod two pi works") = forAll(genAngleBetween0And2Pi,
    Gen.choose[Int](-1000, 1000)) { case (angle, multiple) =>
    Rad.mod2Pi(angle + (multiple.toDouble * Rad.TWO_PI_AS_DOUBLE)) ~== angle
  }

  // Range is inclusive
  val genAngleBetween0And2Pi: Gen[Double] = Gen.choose[Double](0, Rad.TWO_PI_AS_DOUBLE - Default.value)
}
