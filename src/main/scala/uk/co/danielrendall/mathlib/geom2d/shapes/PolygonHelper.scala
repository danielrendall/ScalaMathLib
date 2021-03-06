package uk.co.danielrendall.mathlib.geom2d.shapes

import uk.co.danielrendall.mathlib.geom2d.shapes.PolygonHelper.ParamLine
import uk.co.danielrendall.mathlib.geom2d.{BoundingBox, Line, Point}
import uk.co.danielrendall.mathlib.util.epsilon.Default

import scala.annotation.tailrec

trait PolygonHelper[T <: Shape[T]] { self: Shape[T] =>

  def points: Seq[Point]

  override lazy val perimeter: Double = lines.map(_.length).sum

  override def evaluate(parameter: Double): Point = {
    @tailrec
    def find(last: ParamLine, remaining: Seq[ParamLine]): ParamLine = {
      remaining.headOption match {
        case Some(head) if head.start <= parameter =>
          find(head, remaining.tail)
        case _ => last
      }
    }
    paramLines.headOption.map(head => find(head, paramLines.tail)) match {
      case Some(line) =>
        line.line.evaluate((parameter - line.start) / (line.end - line.start))
      case None =>
        throw new IllegalArgumentException("Bad parameter: " + parameter)
    }
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

  lazy val paramLines: Seq[ParamLine] = {
    @tailrec
    def generate(total: Double, remaining: Seq[Line], accum: List[ParamLine]): List[ParamLine] =
      remaining.headOption match {
        case Some(line) =>
          val newTotal = total + line.length
          generate(newTotal, remaining.tail, ParamLine(total / perimeter, newTotal / perimeter, line) :: accum)
        case None =>
          accum.reverse
      }
    generate(0, lines, List.empty)
  }


}

object PolygonHelper {
  case class ParamLine(start: Double, end: Double, line: Line) {
    assert(0 <= start && start <= 1.0, s"Start was $start")
    assert(0 <= end && end <= 1.0, s"End was $end")
    assert(start < end)
  }
}
