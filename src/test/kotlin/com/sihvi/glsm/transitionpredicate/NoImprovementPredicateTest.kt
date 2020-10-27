package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class NoImprovementPredicateTest {
    class MockMemory(override var noImprovementCount: Int) : Memory<Unit, BasicSolution<Unit>>(BasicCurrentState(BasicSolution<Unit>(arrayOf<Unit>(), 0.0)), BasicSolution<Unit>(arrayOf<Unit>(), 0.0))



    companion object {
        val predicate = NoImprovementPredicate<MockMemory>(10)
    }

    @Test
    fun isTerminate() {
        assertTrue(predicate.isSatisfied(memory = MockMemory(10))) {
            "Must terminate when maximum number of iterations without improvement reached"
        }
        assertTrue(predicate.isSatisfied(memory = MockMemory(11))) {
            "Must terminate when maximum number of iterations without improvement reached"
        }
        assertFalse(predicate.isSatisfied(memory = MockMemory(9))) {
            "Must not terminate until maximum number of iterations without improvement reached"
        }
        assertFalse(predicate.isSatisfied(memory = MockMemory(0))) {
            "Must not terminate until maximum number of iterations without improvement reached"
        }
    }
}