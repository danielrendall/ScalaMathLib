package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{ParametricCurve, Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad

trait Shape[T <: Shape[T]] extends ParametricCurve {

  def perimeter: Double

  def rotate(angle: Rad, about: Point): T

  def rotate(angle: Rad): T = rotate(angle, Point.ORIGIN)

  def translate(vec: Vec): T

}
