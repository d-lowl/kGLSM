package space.d_lowl.kglsm.problem

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.absoluteValue

internal class BenchmarkFunctionsKtTest {

    @Test
    fun getAckley() {
        val ackley = getAckley(3)
        fun testAckley(input: Array<Double>, expected: Double, epsilon: Double = 1.0e-15) {
            val output = ackley(input)
            assert((output - expected).absoluteValue < epsilon) { "Expected ${expected}, got $output" }

        }
        testAckley(Array(3) { 0.0 }, 0.0)
        testAckley(Array(3) { 1.0 }, 3.6253849384403627)
        testAckley(Array(3) { 10.0 }, 17.293294335267746)
        testAckley(Array(3) { 32.0 }, 19.96676885453652)
        testAckley(doubleArrayOf(32.0, 10.0, 4.0).toTypedArray(), 19.594642329416885)
    }
}