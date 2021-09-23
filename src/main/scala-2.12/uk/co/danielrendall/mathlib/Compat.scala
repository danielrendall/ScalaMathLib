package uk.co.danielrendall.mathlib

import java.lang

object Compat {

  @inline
  def box(b: Byte): lang.Byte = Byte.box(b)

  @inline
  def box(i: Int): Integer = Int.box(i)

  @inline
  def box(d: Double): lang.Double = Double.box(d)

  @inline
  def box(s: Short): lang.Short = Short.box(s)

  @inline
  def box(b: Boolean): lang.Boolean = Boolean.box(b)


}
