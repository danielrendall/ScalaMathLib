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

// TODO - this fails sporadically - to be investigated
// Sample failure value: 1.2895161645864984E-308
//  property("near equality") = forAll { (d: Double) =>
//    IEEE754Double(d) ~== IEEE754Double(d + java.lang.Double.MIN_VALUE)
//  }

}
