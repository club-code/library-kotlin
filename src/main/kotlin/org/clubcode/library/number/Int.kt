package org.clubcode.library.number

fun Int.pow(pow: Int): Int =
    when {
        pow == 0 -> 1
        pow == 1 -> this
        pow and 1 == 0 -> (this * this).pow(pow / 2)
        else -> this * ((this * this).pow(pow / 2))
    }

tailrec fun gcd(a: Int, b: Int): Int =
    if (b == 0)
        a
    else
        gcd(b, a%b)


fun lcm(a: Int, b: Int): Int = kotlin.math.abs(a * b)/gcd(a, b)

/**
 * @return (d, u, v) where d is the gcd of a and b, u and v two integers with au + bv = d
 */
fun euclid(a: Int, b: Int): Euclid =
    if (b == 0)
        Euclid(a, 1, 0)
    else {
        val r = euclid(b, a % b)
        Euclid(r.gcd, r.v, r.u - (a/b)*r.v)
    }

data class Euclid(val gcd: Int, val u: Int, val v: Int)

/*


LONG



 */


fun Long.pow(pow: Long): Long =
    when {
        pow == 0L -> 1L
        pow == 1L -> this
        pow and 1 == 0L -> (this * this).pow(pow / 2)
        else -> this * ((this * this).pow(pow / 2))
    }


tailrec fun gcd(a: Long, b: Long): Long =
    if (b == 0L)
        a
    else
        gcd(b, a%b)


fun lcm(a: Long, b: Long): Long = kotlin.math.abs(a * b)/gcd(a, b)

/**
 * @return (d, u, v) where d is the gcd of a and b, u and v two integers with au + bv = d
 */
fun euclid(a: Long, b: Long): EuclidLong =
    if (b == 0L)
        EuclidLong(a, 1, 0)
    else {
        val r = euclid(b, a % b)
        EuclidLong(r.gcd, r.v, r.u - (a/b)*r.v)
    }

data class EuclidLong(val gcd: Long, val u: Long, val v: Long)
