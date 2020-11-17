package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace
import kotlin.random.Random

enum class IIMode {
    BEST, FIRST
}

class IterativeImprovementStrategy<T>(private val mode: IIMode, private val updateBest: Boolean = true)
    : Strategy<T, Memory<T, BasicSolution<T>>, DiscreteSearchSpace<T>>() {
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: DiscreteSearchSpace<T>, costFunction: CostFunction<T>) {
        val neighbourhood = searchSpace.getNeighbourhood(memory.currentSolution.solution)
        val bestCost = memory.currentSolution.cost
        var newSolution = memory.currentSolution

        when (mode) {
            IIMode.FIRST -> {
                for (neighbour in neighbourhood) {
                    val cost = costFunction(neighbour)
                    if (cost < bestCost) {
                        newSolution = BasicSolution(neighbour, cost)
                        break
                    }
                }
            }
            IIMode.BEST -> {
                val neighbourhoodSolutions = neighbourhood
                        .map { costFunction(it) }
                        .zip(neighbourhood)
                        .map { BasicSolution(it.second, it.first) }
                val improvement = neighbourhoodSolutions.filter { it.cost < bestCost }.toList()
                newSolution = if (improvement.isEmpty()) {
                    memory.currentSolution
                } else {
                    improvement.minBy { it.cost }!!
                }
            }
        }

        memory.update(newSolution)
        if (updateBest) memory.updateBest()
    }

    override fun toString(): String {
        val subtype = when (mode) {
            IIMode.BEST -> "Best"
            IIMode.FIRST -> "First"
        }
        return "$subtype Iterative Improvement${if (updateBest) " [updating best]" else ""}"
    }
}