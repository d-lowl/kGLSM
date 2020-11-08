package com.sihvi.glsm.problem

import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.sqrt

internal class TSPTest {
    @Test
    fun testTrivial() {
        val instance = TSP(
                listOf(
                        NDPoint(0.0, 0.0),
                        NDPoint(0.0, 1.0)
                ),
                cycle = false
        )
        assert(instance.evaluate(arrayOf(0, 1)) == 1.0)
    }

    @Test
    fun testTrivialCycle() {
        val instance = TSP(
                listOf(
                        NDPoint(0.0, 0.0),
                        NDPoint(0.0, 1.0)
                ),
                cycle = true
        )
        assert(instance.evaluate(arrayOf(0, 1)) == 2.0)
    }

    @Test
    fun testSquare() {
        val instance = TSP(
                listOf(
                        NDPoint(0.0, 0.0),
                        NDPoint(0.0, 1.0),
                        NDPoint(1.0, 1.0),
                        NDPoint(1.0, 0.0)
                ),
                cycle = false
        )

        val sqrt2 = sqrt(2.0)
        val epsilon = 0.0001
        assert(instance.evaluate(arrayOf(0, 1, 2, 3)) == 3.0)
        assert((instance.evaluate(arrayOf(0, 1, 3, 2)) - (2.0 + sqrt2)).absoluteValue < epsilon)
        assert((instance.evaluate(arrayOf(0, 2, 1, 3)) - (1.0 + 2 * sqrt2)).absoluteValue < epsilon)
    }

    @Test
    fun testSquareCycle() {
        val instance = TSP(
                listOf(
                        NDPoint(0.0, 0.0),
                        NDPoint(0.0, 1.0),
                        NDPoint(1.0, 1.0),
                        NDPoint(1.0, 0.0)
                ),
                cycle = true
        )

        val sqrt2 = sqrt(2.0)
        val epsilon = 0.0001
        assert(instance.evaluate(arrayOf(0, 1, 2, 3)) == 4.0)
        assert((instance.evaluate(arrayOf(0, 1, 3, 2)) - (2.0 + 2 * sqrt2)).absoluteValue < epsilon)
        assert((instance.evaluate(arrayOf(0, 2, 1, 3)) - (2.0 + 2 * sqrt2)).absoluteValue < epsilon)
    }
}