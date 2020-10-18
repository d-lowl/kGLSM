package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.SingleStateMemory
import com.sihvi.glsm.memory.SingleStateSolution
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace
import kotlin.random.Random

enum class ILSMode {
    BEST, RANDOM
}

class IterativeLocalSearch<T>(
        private val space: DiscreteSearchSpace<T>,
        private val problem: Problem<T>,
        private val mode: ILSMode
) : Strategy<T, SingleStateMemory<T>>(space, problem) {
    override fun step(memory: SingleStateMemory<T>) {
        val neighbourhood = space.getNeighbourhood(memory.getCurrentSolution().solution)
        val neighbourhoodSolutions = neighbourhood
                .map { problem.evaluate(it) }
                .zip(neighbourhood)
                .map { SingleStateSolution(it.second, it.first) }
        val bestCost = memory.getBestSolution().cost
        val improvement = neighbourhoodSolutions.filter { it.cost < bestCost }.toList()
        val newSolution = if (improvement.isEmpty()) {
            memory.getCurrentSolution()
        } else {
            when(mode) {
                ILSMode.BEST -> improvement.minBy { it.cost }!!
                ILSMode.RANDOM -> improvement[Random.nextInt(improvement.size)]
            }
        }
        memory.update(newSolution)
    }
}