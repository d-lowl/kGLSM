package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.space.SearchSpace

class RandomWalkStrategy<T>(private val updateBest: Boolean = false) : Strategy<T, BasicSolution<T>>() {
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val newSolution = searchSpace.getRandomNeighbour(memory.currentSolution.solution)
        val newCost = costFunction(newSolution)
        memory.update(BasicSolution(newSolution, newCost))
        if (updateBest) memory.updateBest()
    }

    override fun toString(): String = "Random Walk${if (updateBest) " [updating best]" else ""}"
}