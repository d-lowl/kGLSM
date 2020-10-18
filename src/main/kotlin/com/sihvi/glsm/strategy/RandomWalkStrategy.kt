package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.SingleStateMemory
import com.sihvi.glsm.memory.SingleStateSolution
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace

class RandomWalkStrategy<T>(
        private val space: DiscreteSearchSpace<T>,
        private val problem: Problem<T>
) : Strategy<T, SingleStateMemory<T>>(space, problem) {
    override fun step(memory: SingleStateMemory<T>) {
        val newSolution = space.getRandomNeighbour(memory.getCurrentSolution().solution)
        val newCost = problem.evaluate(newSolution)
        memory.update(SingleStateSolution(newSolution, newCost))
    }
}