package com.sihvi.glsm.evolutionaryalgorithms

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.memory.PopulationSolution
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import kotlin.random.Random

class Mutation<T>(private val mutationFunction: (Array<T>) -> Array<T>): Strategy<T, Memory<T, PopulationSolution<T>>, SearchSpace<T>>() {
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, problem: Problem<T>) {
        memory.currentSolution = memory.currentSolution.map { mutationFunction(it.solution) }.map { BasicSolution(it, problem.evaluate(it)) }
    }

    companion object {
        fun bitFlipMutation(probability: Double): Mutation<Boolean> =
                Mutation { arr -> arr.map { if (Random.nextDouble() < probability) !it else it }.toTypedArray() }
    }
}