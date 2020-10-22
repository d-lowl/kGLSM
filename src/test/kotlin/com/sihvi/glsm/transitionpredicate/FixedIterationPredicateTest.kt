package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.HasStepCount
import com.sihvi.glsm.memory.IMemory
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FixedIterationPredicateTest {
    inner class Memory(private val _stepCount: Int) : IMemory<Unit>, HasStepCount {
        override fun getBestSolution(): Unit = TODO("Not yet implemented")
        override fun getCurrentSolution(): Unit = TODO("Not yet implemented")
        override fun update(solution: Unit) = TODO("Not yet implemented")

        override fun getStepCount(): Int = _stepCount
    }


    companion object {
        val predicate = FixedIterationPredicate<Memory>(10)
    }


    @Test
    fun isTerminate() {
        assertTrue(predicate.isSatisified(memory = Memory(10))) {
            "Must terminate when maximum number of iterations reached"
        }
        assertTrue(predicate.isSatisified(memory = Memory(11))) {
            "Must terminate when maximum number of iterations reached"
        }
        assertFalse(predicate.isSatisified(memory = Memory(9))) {
            "Must not terminate until maximum number of iterations reached"
        }
        assertFalse(predicate.isSatisified(memory = Memory(0))) {
            "Must not terminate until maximum number of iterations reached"
        }
    }
}