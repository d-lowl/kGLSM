package com.sihvi.glsm.evolutionaryalgorithms

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.memory.PopulationSolution
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import kotlin.random.Random

class Mutation<T>(private val name: String = "UNK", private val mutationFunction: (Array<T>) -> Array<T>): Strategy<T, PopulationSolution<T>>() {
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.currentSolution = memory.currentSolution.map { mutationFunction(it.solution) }.map { BasicSolution(it, costFunction(it)) }
    }

    override fun toString(): String = "Mutation: $name"

    companion object {
        fun bitFlipMutation(probability: Double): Mutation<Boolean> =
                Mutation("Bit-Flip") { arr -> arr.map { if (Random.nextDouble() < probability) !it else it }.toTypedArray() }
    }
}