package uk.co.danielrendall.mathlib
import uk.co.danielrendall.mathlib.util.Epsilon
import uk.co.danielrendall.mathlib.util.epsilon.Default

trait UsesDefaultEpsilon {
  implicit val epsilon: Epsilon = Default
}
