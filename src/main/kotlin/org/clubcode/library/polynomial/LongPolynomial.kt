package org.clubcode.library.polynomial

import kotlin.math.max

class LongPolynomial(vararg var coefficients: Long) {
    init {
        reduce()
    }

    val degree: Int
        get() = coefficients.size - 1

    private fun reduce() {
        var i = coefficients.lastIndex
        while (coefficients[i] == 0L && i > 0) {
            i--
        }
        if (i < coefficients.lastIndex) {
            coefficients = LongArray(i+1) { coefficients[it] }
        }
    }

    fun getIndice(i: Int): Long = when (i) {
        in Long.MIN_VALUE until 0 -> throw IllegalArgumentException("Indice are positives")
        in 0..degree -> coefficients[i]
        else -> 0
    }

    operator fun get(x: Long): Long {
        var i = degree
        var result = 0L
        while (i >= 0) {
            result = result * x + getIndice(i)
            i--
        }
        return result
    }

    operator fun times(o: LongPolynomial): LongPolynomial {
        val size = degree + o.degree + 1
        val m = max(degree, o.degree)

        return LongPolynomial(
            *LongArray(size) {
                (if (it <= m) (0..it) else ((it - m)..m))
                    // o.getIndice(m - i + (it-m)) == o.getIndice(it - i)
                    .map { i -> getIndice(i) * o.getIndice(it - i) }
                    .sum()
            }
        )
    }

    operator fun plus(o: LongPolynomial): LongPolynomial {
        val size = max(degree, o.degree) + 1

        return LongPolynomial(*LongArray(size) { getIndice(it) + o.getIndice(it) })
    }

    operator fun minus(o: LongPolynomial): LongPolynomial {
        val size = max(degree, o.degree) + 1

        return LongPolynomial(*LongArray(size) { getIndice(it) - o.getIndice(it) })
    }

    operator fun div(x: Long): LongPolynomial = LongPolynomial(*coefficients.map { it / x }.toLongArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LongPolynomial) return false

        if (!coefficients.contentEquals(other.coefficients)) return false

        return true
    }

    override fun hashCode(): Int {
        return coefficients.contentHashCode()
    }

    override fun toString(): String {
        return coefficients.withIndex().reversed().joinToString(" + ") {
            when(it.index) {
                0 -> "${it.value}"
                1 ->"${it.value}*X"
                else -> "${it.value}*X^${it.index}"
            }
        }
    }
}

operator fun Long.times(p: LongPolynomial): LongPolynomial = LongPolynomial(*p.coefficients.map { this * it }.toLongArray())

fun List<Long>.toPolynomial(): LongPolynomial = LongPolynomial(*this.toLongArray())
fun Array<Long>.toPolynomial(): LongPolynomial = LongPolynomial(*this.toLongArray())

