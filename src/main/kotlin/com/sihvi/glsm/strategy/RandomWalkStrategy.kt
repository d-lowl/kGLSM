package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.SingleStateMemory
import com.sihvi.glsm.memory.SingleStateSolution
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace

class RandomWalkStrategy<T> : Strategy<T, SingleStateMemory<T>, DiscreteSearchSpace<T>>() {
    override fun step(memory: SingleStateMemory<T>, space: DiscreteSearchSpace<T>, problem: Problem<T>) {
        val newSolution = space.getRandomNeighbour(memory.getCurrentSolution().solution)
        val newCost = problem.evaluate(newSolution)
        memory.update(SingleStateSolution(newSolution, newCost))
    }
}