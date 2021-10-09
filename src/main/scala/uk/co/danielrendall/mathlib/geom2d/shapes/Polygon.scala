package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.{BoundingBox, Line, Point, Vec}
import uk.co.danielrendall.mathlib.util.Rad
import uk.co.danielrendall.mathlib.util.epsilon.Default

import scala.annotation.tailrec

case class Polygon(points: Seq[Point]) extends Shape[Polygon] {

  override lazy val perimeter: Double = lines.map(_.length).sum

  override def rotate(angle: Rad, about: Point): Shape[Polygon] =
    copy(points = points.map(_.rotate(angle, about)))

  override def translate(vec: Vec): Shape[Polygon] =
    copy(points = points.map(_.displace(vec)))

  override def evaluate(parameter: Double): Point = rangesAndLines.find(x => x._1 <= parameter) match {
    case Some((start, end, line)) =>
      line.evaluate((parameter - start) * (end - start))

    case None =>
      // should never happen...
      throw new IllegalArgumentException("Bad parameter: " + parameter)
  }

  override def getBoundingBox: BoundingBox =
    BoundingBox.containing(points:_*)

  lazy val lines: Seq[Line] = {
    points.headOption match {
      case Some(start) =>
        @tailrec
        def build(last: Point, remaining: Seq[Point], accum: List[Line]): List[Line] =
          remaining.headOption match {
            case Some(next) =>
              build(next, remaining.tail, Line(last, next) :: accum)
            case None =>
              (Line(last, start) :: accum).reverse
          }
        build(start, points.tail, List.empty)

      case None =>
        Seq.empty
    }
  }

  lazy val rangesAndLines: Seq[(Double, Double, Line)] = {
    def generate(total: Double, remaining: Seq[Line], accum: List[(Double, Double, Line)]): List[(Double, Double, Line)] =
      remaining.headOption match {
        case Some(line) =>
          val newTotal = total + line.length
          generate(newTotal, remaining.tail, (total, newTotal, line) :: accum)
        case None =>
          accum.reverse
      }
    generate(0, lines, List.empty)
  }

}

object Polygon {

  def apply(point: Point, points: Point*): Polygon = Polygon(point +: points)

}
