package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.HasNoImprovementCount
import com.sihvi.glsm.memory.IMemory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


internal class NoImprovementPredicateTest {
    inner class Memory(private val noImprovementCount: Int) : IMemory<Unit>, HasNoImprovementCount {
        override fun getBestSolution(): Unit = TODO("Not yet implemented")
        override fun getCurrentSolution(): Unit = TODO("Not yet implemented")
        override fun update(solution: Unit) = TODO("Not yet implemented")

        override fun getNoImprovementCount(): Int = noImprovementCount
    }


    companion object {
        val predicate = NoImprovementPredicate<Memory>(10)
    }

    @Test
    fun isTerminate() {
        assertTrue(predicate.isTerminate(memory = Memory(10))) {
            "Must terminate when maximum number of iterations without improvement reached"
        }
        assertTrue(predicate.isTerminate(memory = Memory(11))) {
            "Must terminate when maximum number of iterations without improvement reached"
        }
        assertFalse(predicate.isTerminate(memory = Memory(9))) {
            "Must not terminate until maximum number of iterations without improvement reached"
        }
        assertFalse(predicate.isTerminate(memory = Memory(0))) {
            "Must not terminate until maximum number of iterations without improvement reached"
        }
    }
}