package uk.co.danielrendall.mathlib.geom2d

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import uk.co.danielrendall.mathlib.util.Generators.genAngleBetween0And2Pi
import uk.co.danielrendall.mathlib.util.Implicits.DoubleOps
import uk.co.danielrendall.mathlib.util.Rad
import uk.co.danielrendall.mathlib.util.epsilon.Default

class ComplexProps extends Properties("Complex") {

  property("arg") = forAll(Gen.choose[Double](0.1, 1000d), genAngleBetween0And2Pi) { case (mod, arg) =>
    Complex.modArg(mod, Rad(arg)).arg.angle ~== arg
  }

}
