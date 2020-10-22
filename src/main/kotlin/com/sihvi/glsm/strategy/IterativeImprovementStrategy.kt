package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.SingleStateMemory
import com.sihvi.glsm.memory.SingleStateSolution
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace
import kotlin.random.Random

enum class IIMode {
    BEST, RANDOM
}

class IterativeImprovementStrategy<T>(
        private val mode: IIMode
) : Strategy<T, SingleStateMemory<T>, DiscreteSearchSpace<T>>() {
    override fun step(memory: SingleStateMemory<T>, space: DiscreteSearchSpace<T>, problem: Problem<T>) {
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
                IIMode.BEST -> improvement.minBy { it.cost }!!
                IIMode.RANDOM -> improvement[Random.nextInt(improvement.size)]
            }
        }
        memory.update(newSolution)
    }
}