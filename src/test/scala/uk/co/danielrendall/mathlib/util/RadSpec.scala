package uk.co.danielrendall.mathlib.util

import org.specs2.mutable.Specification
import uk.co.danielrendall.mathlib.util.Implicits.RadOps
import epsilon.Default
import org.specs2.specification.core.Fragments
import uk.co.danielrendall.mathlib.ApproxMatchers

class RadSpec extends Specification with ApproxMatchers {

  "Rad" should {
    Fragments(
      Seq(
        Trip(0.1, 0.2, 0.3),
        Trip(0.2, 0.4, 0.6),
        Trip(0.4, 0.8, 1.2),
        Trip(0.5, 1.0, 1.5),
        Trip(1.0, 2.0, 3.0),
        Trip(1.5, 3.0, 4.5),
        Trip(2.0, 4.0, 6.0),
        Trip(3.0, 6.0, 2.71681469282),
        Trip(4.0, 8.0, 5.71681469282),
        Trip(5.0, 10.0, 2.43362938564)
      ).flatMap { trip =>
        Seq(
          s"${trip.a} + ${trip.b} = ${trip.c}" in {
            (Rad(trip.a) + Rad(trip.b) must be_~==(Rad(trip.c)))
          },
          s"${trip.b} + ${trip.a} = ${trip.c}" in {
            (Rad(trip.b) + Rad(trip.a) must be_~==(Rad(trip.c)))
          },
          s"${trip.c} - ${trip.b} = ${trip.a}" in {
            (Rad(trip.c) - Rad(trip.b) must be_~==(Rad(trip.a)))
          },
          s"${trip.c} - ${trip.a} = ${trip.b}" in {
            (Rad(trip.c) - Rad(trip.a) must be_~==(Rad(trip.b)))
          }
        )
      }:_*
    )
  }

case class Trip(a: Double, b: Double, c: Double)
}

