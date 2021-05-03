package uk.co.danielrendall.mathlib.util

import org.specs2.mutable.Specification

class IEEE754DoubleSpec extends Specification {

  "SigExp" should {
    "Work for -16.0d" in {
      IEEE754Double(-16.0d) must_=== IEEE754Double(false, 1L, 4)
    }
    "Work for -8.0d" in {
      IEEE754Double(-8.0d) must_=== IEEE754Double(false, 1L, 3)
    }
    "Work for -4.0d" in {
      IEEE754Double(-4.0d) must_=== IEEE754Double(false, 1L, 2)
    }
    "Work for -2.0d" in {
      IEEE754Double(-2.0d) must_=== IEEE754Double(false, 1L, 1)
    }
    "Work for -1.0d" in {
      IEEE754Double(-1.0d) must_=== IEEE754Double(false, 1L, 0)
    }
    "Work for -0.5d" in {
      IEEE754Double(-0.5d) must_=== IEEE754Double(false, 1L, -1)
    }
    "Work for -0.25d" in {
      IEEE754Double(-0.25d) must_=== IEEE754Double(false, 1L, -2)
    }
    "Work for -0.125d" in {
      IEEE754Double(-0.125d) must_=== IEEE754Double(false, 1L, -3)
    }
    "Work for -0.0675d" in {
      IEEE754Double(-0.0625d) must_=== IEEE754Double(false, 1L, -4)
    }
    // ZERO
    "Work for 0.0d" in {
      IEEE754Double(0.0d) must_=== IEEE754Double(true, 1L, -1023)
    }
    "Work for 0.0675d" in {
      IEEE754Double(0.0625d) must_=== IEEE754Double(true, 1L, -4)
    }
    "Work for 0.125d" in {
      IEEE754Double(0.125d) must_=== IEEE754Double(true, 1L, -3)
    }
    "Work for 0.25d" in {
      IEEE754Double(0.25d) must_=== IEEE754Double(true, 1L, -2)
    }
    "Work for 0.5d" in {
      IEEE754Double(0.5d) must_=== IEEE754Double(true, 1L, -1)
    }
    "Work for 1.0d" in {
      IEEE754Double(1.0d) must_=== IEEE754Double(true, 1L, 0)
    }
    "Work for 2.0d" in {
      IEEE754Double(2.0d) must_=== IEEE754Double(true, 1L, 1)
    }
    "Work for 4.0d" in {
      IEEE754Double(4.0d) must_=== IEEE754Double(true, 1L, 2)
    }
    "Work for 8.0d" in {
      IEEE754Double(8.0d) must_=== IEEE754Double(true, 1L, 3)
    }
    "Work for 16.0d" in {
      IEEE754Double(16.0d) must_=== IEEE754Double(true, 1L, 4)
    }
  }

}
