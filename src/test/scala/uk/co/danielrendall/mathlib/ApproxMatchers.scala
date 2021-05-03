package uk.co.danielrendall.mathlib

import org.specs2.matcher.{Expectable, MatchResult, Matcher}
import uk.co.danielrendall.mathlib.util.Epsilon
import uk.co.danielrendall.mathlib.util.Implicits.DoubleOps

trait ApproxMatchers {

  def beApproximately(d: Double)
                     (implicit epsilon: Epsilon): Matcher[Double] = new Matcher[Double] {
    override def apply[S <: Double](t: Expectable[S]): MatchResult[S] =
      result(d ~== t.value, t.value + " ~== " + d, t.value + " !~== " + d, t)
  }

}
