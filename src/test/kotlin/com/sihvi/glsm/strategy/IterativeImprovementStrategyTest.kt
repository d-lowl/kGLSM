package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.BasicMemory
import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.problem.MAXSAT
import com.sihvi.glsm.problem.SATClause
import com.sihvi.glsm.problem.SATLiteral
import com.sihvi.glsm.space.BooleanSearchSpace
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class IterativeImprovementStrategyTest {
    lateinit var memory: BasicMemory<Boolean>
    lateinit var searchSpace: BooleanSearchSpace
    lateinit var problem: MAXSAT
    @BeforeEach
    fun setUp() {
        problem = MAXSAT(
                4,
                List(5){ it1 -> SATClause(Array(it1+1) { SATLiteral(it,false) }) }
        )
        searchSpace = BooleanSearchSpace(5)
        val initialSolution = Array(5) {false}
        memory = BasicMemory(BasicSolution(initialSolution, problem.evaluate(initialSolution)))
    }

    @Test
    fun stepBestImprovement() {
        val strategy = IterativeImprovementStrategy<Boolean>(IIMode.BEST)
        strategy.step(memory, searchSpace, problem::evaluate)
        assert(arrayOf(true, false, false, false, false).contentEquals(memory.currentSolution.solution))
    }

    @Test
    fun stepRandomImprovement() {
        val strategy = IterativeImprovementStrategy<Boolean>(IIMode.FIRST)
        strategy.step(memory, searchSpace, problem::evaluate)
        assert(memory.currentSolution.solution.filter { it }.size == 1)
    }

    @Test
    fun stepNoImprovement() {
        val strategy = IterativeImprovementStrategy<Boolean>(IIMode.FIRST)
        val initialSolution = arrayOf(true, false, false, false, false)
        memory = BasicMemory(BasicSolution(initialSolution, problem.evaluate(initialSolution)))
        strategy.step(memory, searchSpace, problem::evaluate)
        assert(arrayOf(true, false, false, false, false).contentEquals(memory.currentSolution.solution))
    }
}