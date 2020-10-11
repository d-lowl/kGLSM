package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace

class RandomWalkStrategy<T>(
        private val space: DiscreteSearchSpace<T>,
        private val problem: Problem<T>,
        private val memory: Memory<T>)
    : Strategy<T>(space, problem, memory) {
    override fun step() {
        val newSolution = space.getRandomNeighbour(memory.currentSolution)
        val newCost = problem.evaluate(newSolution)
        memory.update(newSolution, newCost)
    }
}