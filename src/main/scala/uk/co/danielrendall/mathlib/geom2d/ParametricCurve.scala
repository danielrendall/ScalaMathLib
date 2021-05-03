package uk.co.danielrendall.mathlib.geom2d

/**
 * @author Daniel Rendall <drendall@gmail.com>
 * @since 23-May-2009 18:38:47
 */
trait ParametricCurve {

  def evaluate(parameter: Double): Point

  def getBoundingBox: BoundingBox

}
