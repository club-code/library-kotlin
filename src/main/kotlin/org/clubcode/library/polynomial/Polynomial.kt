package org.clubcode.library.polynomial

import org.clubcode.library.vector.Vector2
import kotlin.math.max

class Polynomial(vararg var coefficients: Double) {
    init {
        reduce()
    }

    val degree: Int
        get() = coefficients.size - 1

    private fun reduce() {
        var i = coefficients.lastIndex
        while (coefficients[i] == 0.0 && i > 0) {
            i--
        }
        if (i < coefficients.lastIndex) {
            coefficients = DoubleArray(i+1) { coefficients[it] }
        }
    }

    fun getIndice(i: Int): Double = when (i) {
        in Int.MIN_VALUE until 0 -> throw IllegalArgumentException("Indice are positives")
        in 0..degree -> coefficients[i]
        else -> 0.0
    }

    operator fun get(x: Double): Double {
        var i = degree
        var result = 0.0
        while (i >= 0) {
            result = result * x + getIndice(i)
            i--
        }
        return result
    }

    operator fun times(o: Polynomial): Polynomial {
        val size = degree + o.degree + 1
        val m = max(degree, o.degree)

        return Polynomial(
            *DoubleArray(size) {
                (if (it <= m) (0..it) else ((it - m)..m))
                    // o.getIndice(m - i + (it-m)) == o.getIndice(it - i)
                    .map { i -> getIndice(i) * o.getIndice(it - i) }
                    .sum()
            }
        )
    }

    operator fun plus(o: Polynomial): Polynomial {
        val size = max(degree, o.degree) + 1

        return Polynomial(*DoubleArray(size) { getIndice(it) + o.getIndice(it) })
    }

    operator fun minus(o: Polynomial): Polynomial {
        val size = max(degree, o.degree) + 1

        return Polynomial(*DoubleArray(size) { getIndice(it) - o.getIndice(it) })
    }

    operator fun div(x: Double): Polynomial = Polynomial(*coefficients.map { it / x }.toDoubleArray())

    operator fun div(o: Polynomial): Polynomial = euclideanDivision(o).first

    operator fun rem(o: Polynomial): Polynomial = euclideanDivision(o).second

    fun euclideanDivision(o: Polynomial): Pair<Polynomial, Polynomial> {
        var A = this
        val result = DoubleArray(A.degree - o.degree + 1)

        while (A.degree >= o.degree) {
            val c = A.coefficients.last() / o.coefficients.last()
            val i = A.coefficients.lastIndex - o.coefficients.lastIndex
            A -= c*Polynomial(*o.coefficients.shl(i))
            result[i] = c
        }

        return Polynomial(*result) to A
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Polynomial) return false

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

operator fun Double.times(p: Polynomial): Polynomial = Polynomial(*p.coefficients.map { this * it }.toDoubleArray())

fun List<Double>.toPolynomial(): Polynomial = Polynomial(*this.toDoubleArray())
fun Array<Double>.toPolynomial(): Polynomial = Polynomial(*this.toDoubleArray())
fun DoubleArray.shl(n: Int): DoubleArray =
    DoubleArray(this.size + n) {
        if (it >= n) this[it-n] else 0.0
    }

fun List<Vector2>.toLagrangePolynomial(): Polynomial {
    return this.mapIndexed { j, u ->
        u.y * this.filterIndexed { i, _ -> i != j }
            .map { v -> Polynomial(-v.x, 1.0)/(u.x - v.x) }
            .reduce { acc, p -> acc*p }
    }.reduce { acc, p -> acc+p }
}
