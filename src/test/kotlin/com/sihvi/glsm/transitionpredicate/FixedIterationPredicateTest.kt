package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FixedIterationPredicateTest {
    class MockMemory(override var stepCount: Int) : Memory<Unit, BasicSolution<Unit>>(BasicCurrentState(BasicSolution<Unit>(arrayOf<Unit>(), 0.0)), BasicSolution<Unit>(arrayOf<Unit>(), 0.0), listOf())

    companion object {
        val predicate = FixedIterationPredicate(10)
    }


    @Test
    fun isTerminate() {
        assertTrue(predicate.isSatisfied(memory = MockMemory(10))) {
            "Must terminate when maximum number of iterations reached"
        }
        assertTrue(predicate.isSatisfied(memory = MockMemory(11))) {
            "Must terminate when maximum number of iterations reached"
        }
        assertFalse(predicate.isSatisfied(memory = MockMemory(9))) {
            "Must not terminate until maximum number of iterations reached"
        }
        assertFalse(predicate.isSatisfied(memory = MockMemory(0))) {
            "Must not terminate until maximum number of iterations reached"
        }
    }
}