package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace
import kotlin.random.Random

enum class IIMode {
    BEST, RANDOM
}

class IterativeImprovementStrategy<T>(private val mode: IIMode, private val updateBest: Boolean = true)
    : Strategy<T, Memory<T, BasicSolution<T>>, DiscreteSearchSpace<T>>() {
    override fun step(memory: Memory<T, BasicSolution<T>>, space: DiscreteSearchSpace<T>, problem: Problem<T>) {
        val neighbourhood = space.getNeighbourhood(memory.currentSolution.solution)
        val neighbourhoodSolutions = neighbourhood
                .map { problem.evaluate(it) }
                .zip(neighbourhood)
                .map { BasicSolution(it.second, it.first) }
        val bestCost = memory.currentSolution.cost
        val improvement = neighbourhoodSolutions.filter { it.cost < bestCost }.toList()
        val newSolution = if (improvement.isEmpty()) {
            memory.currentSolution
        } else {
            when(mode) {
                IIMode.BEST -> improvement.minBy { it.cost }!!
                IIMode.RANDOM -> improvement[Random.nextInt(improvement.size)]
            }
        }
        memory.update(newSolution)
        if (updateBest) memory.updateBest()
    }
}