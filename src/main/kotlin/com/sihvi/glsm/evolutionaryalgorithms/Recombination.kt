package com.sihvi.glsm.evolutionaryalgorithms

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.memory.PopulationSolution
import com.sihvi.glsm.memory.pickRandom
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import kotlin.random.Random

class Recombination<T>(private val function: (Array<T>, Array<T>) -> Array<T>) : Strategy<T, Memory<T, PopulationSolution<T>>, SearchSpace<T>>() {


    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, problem: Problem<T>) {
        memory.currentSolution = List(memory.currentSolution.size) {
            function(memory.currentSolution.pickRandom(), memory.currentSolution.pickRandom())
        }.map { BasicSolution(it, problem.evaluate(it)) }
    }

    companion object {
        fun <T> singlePointCrossover(a: Array<T>, b: Array<T>): Array<T> {
            require(a.size == b.size)
            val crossoverPoint = Random.nextInt(a.size)
            return a.sliceArray(0 until crossoverPoint) + a.sliceArray(crossoverPoint until a.size)
        }

        fun <T> singlePointCrossoverRecombination(): Recombination<T> = Recombination(::singlePointCrossover)
    }
}