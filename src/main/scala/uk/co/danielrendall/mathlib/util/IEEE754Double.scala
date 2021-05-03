package uk.co.danielrendall.mathlib.util

import uk.co.danielrendall.mathlib.util.IEEE754Double.{EXP_BIAS, SIGNIF_BIT_MASK, SIGN_BIT_MASK}

case class IEEE754Double(sign: Boolean, frac: Long, exp: Short) {

  private val significantBitsToCompare = 30

  def ~==(other: IEEE754Double): Boolean = {
    if (sign != other.sign) {
      false
    } else if (exp + 1 == other.exp) {
      ~==(other.decreaseExponentBy1)
    } else if (exp - 1 == other.exp) {
      decreaseExponentBy1.~==(other)
    } else if (exp == other.exp) {
      val bits = java.lang.Long.numberOfLeadingZeros(frac)

      if (bits < significantBitsToCompare) {
        frac == other.frac
      } else {
        (frac >> (bits - significantBitsToCompare)) == (other.frac >> (bits - significantBitsToCompare))
      }

    } else {
      false
    }
  }

  private def decreaseExponentBy1: IEEE754Double = IEEE754Double(sign, frac << 1, (exp - 1).toShort)

  def toDouble: Double = {
    val signBits = if (sign) 0 else SIGN_BIT_MASK
    val expBits = (exp + EXP_BIAS).toLong << 52
    val fracBits = (frac - 1) & SIGNIF_BIT_MASK
    java.lang.Double.longBitsToDouble(signBits | expBits | fracBits)
  }

}

object IEEE754Double {

  // Stolen from sun.misc.DoubleConsts
  val SIGN_BIT_MASK = 0x8000000000000000L
  val EXP_BIT_MASK = 0x7FF0000000000000L
  val SIGNIF_BIT_MASK = 0x000FFFFFFFFFFFFFL
  val EXP_BIAS: Short = 1023.toShort
  val SIGNIFICAND_WIDTH = 53


  def apply(double: Double): IEEE754Double = {
    val bits = java.lang.Double.doubleToRawLongBits(double)
    val exp: Short = (((bits & EXP_BIT_MASK) >> 52) - EXP_BIAS).toShort
    val frac: Long = (bits & SIGNIF_BIT_MASK) + 1
    val sign: Boolean = (bits & SIGN_BIT_MASK) != SIGN_BIT_MASK
    IEEE754Double(sign, frac, exp)
  }
}
