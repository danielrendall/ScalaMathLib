package uk.co.danielrendall.mathlib

import org.specs2.matcher.{Expectable, MatchResult, Matcher}
import uk.co.danielrendall.mathlib.ApproxMatchers.CanBeComparedApproximately
import uk.co.danielrendall.mathlib.util.{Epsilon, Rad}
import uk.co.danielrendall.mathlib.util.Implicits.{DoubleOps, RadOps}

trait ApproxMatchers {

  def be_~==[T](t1: T)(implicit cbca: CanBeComparedApproximately[T], epsilon: Epsilon): Matcher[T] =
    new Matcher[T] {
      override def apply[S <: T](t: Expectable[S]): MatchResult[S] =
        result(cbca.~==(t1, t.value), t.value + " ~== " + t1, t.value + " !~== " + t1, t)
    }

}

object ApproxMatchers {

  trait CanBeComparedApproximately[T] {
    def ~==(t1: T, t2: T)(implicit epsilon: Epsilon): Boolean
  }

  implicit object DoubleCanBeComparedApproximately extends CanBeComparedApproximately[Double] {
    override def ~==(t1: Double, t2: Double)(implicit epsilon: Epsilon): Boolean = t1 ~== t2
  }

  implicit object RadCanBeComparedApproximately extends CanBeComparedApproximately[Rad] {
    override def ~==(t1: Rad, t2: Rad)(implicit epsilon: Epsilon): Boolean = t1 ~== t2
  }

}
