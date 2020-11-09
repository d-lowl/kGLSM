package com.sihvi.glsm.evolutionaryalgorithms

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.memory.PopulationSolution
import com.sihvi.glsm.memory.attribute.Stash
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy

class Selection<T>(private val name: String = "UNK", private val selectionFunction: (Collection<BasicSolution<T>>, Int) -> List<BasicSolution<T>>): Strategy<T, Memory<T, PopulationSolution<T>>, SearchSpace<T>>() {
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val stash = memory.getAttribute<Stash<BasicSolution<T>>>()
        memory.currentSolution = selectionFunction(stash.stash, memory.currentSolution.size)
        memory.updateBest()
        stash.clearStash()
    }

    override fun toString(): String = "Selection: $name"

    companion object {
        fun <T> elitism(pool: Collection<BasicSolution<T>>, size: Int): List<BasicSolution<T>> =
                pool.sortedBy { it.cost }.take(size)

        fun <T> elitistSelection(): Selection<T> = Selection("Elitist", ::elitism)
    }
}