package uk.co.danielrendall.mathlib.util

import org.scalacheck.Gen
import uk.co.danielrendall.mathlib.util.epsilon.Default

object Generators {
  val genAngleBetween0And2Pi: Gen[Double] = Gen.choose[Double](0, Rad.TWO_PI_AS_DOUBLE - Default.value)
}
