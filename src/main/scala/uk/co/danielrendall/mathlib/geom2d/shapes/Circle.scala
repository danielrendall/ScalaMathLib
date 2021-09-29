package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{BoundingBox, Complex, Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad

case class Circle(center: Point, radius: Double, startAngle: Rad = Rad.zero) extends Shape[Circle] {

  override def perimeter: Double = Math.PI * 2.0 * radius

  override def rotate(angle: Rad, about: Point): Shape[Circle] =
    Circle(center.rotate(angle, about), radius, startAngle + angle)

  override def evaluate(parameter: Double): Point = {
    val angle = startAngle + (parameter * Rad.TWO_PI)
    Point(center.rep.add((Complex.modArg(radius, angle))))
  }

  override def getBoundingBox: BoundingBox =
    BoundingBox(center.x - radius, center.x + radius, center.y  - radius, center.y + radius)

  override def translate(vec: Vec): Circle =
    copy(center = center.displace(vec))
}
