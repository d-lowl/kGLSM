package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace

class RandomWalkStrategy<T>(private val updateBest: Boolean = true) : Strategy<T, Memory<T, BasicSolution<T>>, DiscreteSearchSpace<T>>() {
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: DiscreteSearchSpace<T>, costFunction: CostFunction<T>) {
        val newSolution = searchSpace.getRandomNeighbour(memory.currentSolution.solution)
        val newCost = costFunction(newSolution)
        memory.update(BasicSolution(newSolution, newCost))
        if (updateBest) memory.updateBest()
    }
}