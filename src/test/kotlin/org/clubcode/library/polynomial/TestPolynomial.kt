package org.clubcode.library.polynomial

import org.clubcode.library.vector.Vector2
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TestPolynomial {
    private lateinit var x: Polynomial
    private lateinit var xSquared: Polynomial
    private lateinit var xMinus2: Polynomial
    private lateinit var xPlus1: Polynomial

    @Before
    fun init() {
        x = Polynomial(0.0, 1.0)
        xSquared = Polynomial(0.0, 0.0, 1.0)
        xMinus2 = Polynomial(-2.0, 1.0)
        xPlus1 = Polynomial(1.0, 1.0)
        xPlus1 = Polynomial(1.0, 1.0)
    }

    @Test
    fun testPlus() {
        assertEquals(Polynomial(0.0, 1.0, 1.0), x + xSquared)
    }

    @Test
    fun testTimesPolynomial() {
        assertEquals(Polynomial(-2.0, -1.0, 1.0), xMinus2 * xPlus1)
    }

    @Test
    fun testTimesDouble() {
        assertEquals(Polynomial(0.0, 0.0, 2.0), 2.0 * xSquared)
    }

    @Test
    fun testEuclideanDivision() {
        val A = Polynomial(0.0, -2.0, 3.0, -1.0, -1.0, 1.0)
        val B = Polynomial(1.0, -1.0, 1.0)
        assertEquals(Polynomial(1.0, -2.0, 0.0, 1.0) to Polynomial(-1.0, 1.0), A.euclideanDivision(B))
    }

    @Test
    fun testGet() {
        val p = Polynomial(1.0, 0.0, 1.0)
        val delta = 0.0000001
        assertEquals(1.0, p[0.0], delta)
        assertEquals(5.0, p[2.0], delta)
    }

    @Test
    fun testLagrange() {
        assertEquals(Polynomial(4.0, 0.5, -1.5), listOf(
            Vector2(1.0, 3.0),
            Vector2(-1.0, 2.0),
            Vector2(2.0, -1.0),
        ).toLagrangePolynomial())
    }

    @Test
    fun testPrint() {
        println(Polynomial(-2.0, -1.0, 1.0))
    }
}