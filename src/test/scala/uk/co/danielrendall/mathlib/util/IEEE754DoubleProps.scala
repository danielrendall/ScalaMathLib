package uk.co.danielrendall.mathlib.util

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

class IEEE754DoubleProps extends Properties("IEEE754Double") {

  property("round trip") = forAll { (d: Double) =>
    IEEE754Double(d).toDouble == d
  }

  property("reflexive equality") = forAll { (d: Double) =>
    IEEE754Double(d) ~== IEEE754Double(d)
  }

  property("near equality") = forAll { (d: Double) =>
    IEEE754Double(d) ~== IEEE754Double(d + java.lang.Double.MIN_VALUE)
  }

}
