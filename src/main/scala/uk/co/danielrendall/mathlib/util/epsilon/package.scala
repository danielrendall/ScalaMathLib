package uk.co.danielrendall.mathlib.util

package object epsilon {

  implicit object Default extends Epsilon(10e-10)

}
