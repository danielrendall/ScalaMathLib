package uk.co.danielrendall.mathlib.geom2d

import org.specs2.matcher.Matcher
import org.specs2.matcher.Matchers.beCloseTo

trait ApproxMatchers {

  private val DELTA: Double = 10e-10

  def beApproximately(d: Double): Matcher[Double] = beCloseTo[Double](d, DELTA)

}
