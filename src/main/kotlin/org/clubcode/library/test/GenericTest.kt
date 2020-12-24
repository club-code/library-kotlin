package org.clubcode.library.test

import org.junit.After
import org.junit.Assert
import org.junit.Before
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
import kotlin.reflect.KFunction


interface FastTests
interface NotWorkingTests
interface SlowTests

open class GenericTest {

    lateinit var oldInput: InputStream
    lateinit var oldOutput: PrintStream

    @Before
    fun setUp() {
        oldInput = System.`in`
        oldOutput = System.out
    }

    @After
    fun tearDown() {
        System.setOut(oldOutput)
    }

    fun genericTestString(input: String, output: String, method: KFunction<Unit>) {
        val inp = input.byteInputStream()

        val o = ByteArrayOutputStream(1024 * 1024)

        System.setIn(inp)
        System.setOut(PrintStream(o))

        method.call()

        setOldInput()
        setOldOutput()

        Assert.assertEquals(output.trim(), o.toString().trim())
        o.close()
    }

    fun genericTest(input: String, output: String, method: KFunction<Unit>) {
        val inp = GenericTest::class.java.getResourceAsStream(input)
        val answer = GenericTest::class.java.getResource(output)

        val o = ByteArrayOutputStream(1024 * 1024)

        System.setIn(inp)
        System.setOut(PrintStream(o))

        method.call()

        setOldInput()
        setOldOutput()

        Assert.assertEquals(answer.readText().trim(), o.toString().trim())
        o.close()
    }

    fun genericTry(input: String, method: KFunction<Unit>) {
        val inp = GenericTest::class.java.getResourceAsStream(input)

        val o = ByteArrayOutputStream(1024 * 1024)

        System.setIn(inp)

        method.call()

        setOldInput()

        o.close()
    }

    private fun setOldInput() = System.setIn(oldInput)
    private fun setOldOutput() = System.setOut(oldOutput)

}
