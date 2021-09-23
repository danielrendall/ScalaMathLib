package uk.co.danielrendall.mathlib

object Compat {

  @inline
  def box(b: Byte): Byte = b

  @inline
  def box(i: Int): Int = i

  @inline
  def box(d: Double): Double = d

  @inline
  def box(s: Short): Short = s

  @inline
  def box(b: Boolean): Boolean = b

}
