package org.clubcode.library.math.polynomial

import kotlin.math.max

class IntPolynomial(vararg var coefficients: Int) {
    init {
        reduce()
    }

    val degree: Int
        get() = coefficients.size - 1

    private fun reduce() {
        var i = coefficients.lastIndex
        while (coefficients[i] == 0 && i > 0) {
            i--
        }
        if (i < coefficients.lastIndex) {
            coefficients = IntArray(i+1) { coefficients[it] }
        }
    }

    fun getIndice(i: Int): Int = when (i) {
        in Int.MIN_VALUE until 0 -> throw IllegalArgumentException("Indice are positives")
        in 0..degree -> coefficients[i]
        else -> 0
    }

    operator fun get(x: Int): Int {
        var i = degree
        var result = 0
        while (i >= 0) {
            result = result * x + getIndice(i)
            i--
        }
        return result
    }

    operator fun times(o: IntPolynomial): IntPolynomial {
        val size = degree + o.degree + 1
        val m = max(degree, o.degree)

        return IntPolynomial(
            *IntArray(size) {
                (if (it <= m) (0..it) else ((it - m)..m))
                    // o.getIndice(m - i + (it-m)) == o.getIndice(it - i)
                    .map { i -> getIndice(i) * o.getIndice(it - i) }
                    .sum()
            }
        )
    }

    operator fun plus(o: IntPolynomial): IntPolynomial {
        val size = max(degree, o.degree) + 1

        return IntPolynomial(*IntArray(size) { getIndice(it) + o.getIndice(it) })
    }

    operator fun minus(o: IntPolynomial): IntPolynomial {
        val size = max(degree, o.degree) + 1

        return IntPolynomial(*IntArray(size) { getIndice(it) - o.getIndice(it) })
    }

    operator fun div(x: Int): IntPolynomial = IntPolynomial(*coefficients.map { it / x }.toIntArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IntPolynomial) return false

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

operator fun Int.times(p: IntPolynomial): IntPolynomial = IntPolynomial(*p.coefficients.map { this * it }.toIntArray())

fun List<Int>.toPolynomial(): IntPolynomial = IntPolynomial(*this.toIntArray())
fun Array<Int>.toPolynomial(): IntPolynomial = IntPolynomial(*this.toIntArray())
