package uk.co.danielrendall.mathlib.geom2d

import org.specs2.mutable.Specification
import uk.co.danielrendall.mathlib.util.Rad
import uk.co.danielrendall.mathlib.{ApproxMatchers, UsesDefaultEpsilon}

class ComplexSpec extends Specification
  with ApproxMatchers
  with UsesDefaultEpsilon {

  "Complex" should {
    "Assign X and Y" in {
      val a = Complex(4.5, 0.2)
      a.x must be_~==(4.5)
      a.y must be_~==(0.2)
    }

    "Return modulus and argument" in {
      val a = Complex(4.5, 0.2)
      a.mod must be_~==(Math.sqrt(4.5 * 4.5 + 0.2 * 0.2))
      a.arg must be_~==(Rad(Math.atan(0.2 / 4.5)))
    }

    "Return the correct argument in different quadrants" in {
      val TL = Complex(-2.0, 2.0)
      val TR = Complex(3.0, 3.0)
      val BL = Complex(-1.5, -1.5)
      val BR = Complex(4.567, -4.567)
      TL.arg must be_~==(Rad(3.0 * Math.PI / 4.0))
      TR.arg must be_~==(Rad(Math.PI / 4.0))
      BL.arg must be_~==(Rad(-3.0 * Math.PI / 4.0))
      BR.arg must be_~==(Rad(-Math.PI / 4.0))
    }

    "Add correctly" in {
      val a = Complex(4.5, 0.2)
      val b = Complex(2.1, 9.4)
      val c = a.add(b)
      c.x must be_~==(6.6)
      c.y must be_~==(9.6)
    }

    "Subtract correctly" in {
      val a = Complex(4.5, 0.2)
      val b = Complex(2.1, 9.4)
      val c = a.sub(b)
      c.x must be_~==(2.4)
      c.y must be_~==(-9.2)
    }

    "Scale correctly" in {
      val a = Complex(4.5, 0.2)
      val b = a.times(2.5)
      b.x must be_~==(11.25)
      b.y must be_~==(0.5)
    }

    "Multiply correctly" in {
      val a = Complex(4.5, 0.2)
      val b = Complex(2.1, 9.4)
      val c = a.times(b)
      c.mod must be_~==(a.mod * b.mod)
      c.arg must be_~==(a.arg + b.arg)
    }
  }
}
