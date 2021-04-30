package uk.co.danielrendall.mathlib.util

import scala.annotation.tailrec

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 30-Apr-2010 19:38:47
 */
object Mathlib {

  val TWO_PI: Double = 2.0d * Math.PI
  val PI_BY_TWO: Double = Math.PI / 2.0d
  val THREE_PI_BY_TWO: Double = 3.0d * Math.PI / 2.0d
  val PI_BY_FOUR: Double = Math.PI / 4.0d
  val THREE_PI_BY_FOUR: Double = 3.0d * Math.PI / 4.0d

  val SQRT_TWO: Double = Math.sqrt(2.0d)
  val SQRT_TWO_RECIPROCAL: Double = 1.0d / SQRT_TWO

  val SQRT_THREE: Double = Math.sqrt(3.0d)
  val SQRT_THREE_RECIPROCAL: Double = 1.0d / SQRT_THREE

  val SQRT_FIVE: Double = Math.sqrt(5.0d)
  val SQRT_FIVE_RECIRPOCAL: Double = 1.0d / SQRT_FIVE

  val PHI: Double = (SQRT_FIVE + 1.0d) / 2.0d
  val PHI_RECIPROCAL: Double = 1.0d / PHI

  @tailrec
  def mean(d1: Double, d2: Double): Double = if (d1 <= d2) d1 + (d2 - d1) / 2.0d
  else mean(d2, d1)

  def pythagorus(x: Double, y: Double): Double = Math.sqrt(pythagorusSquared(x, y))

  def pythagorusSquared(x: Double, y: Double): Double = (x * x) + (y * y)


}
