package com.sihvi.glsm.evolutionaryalgorithms

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.memory.PopulationSolution
import com.sihvi.glsm.memory.attribute.Stash
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy

class PushStash<T>: Strategy<T, Memory<T, PopulationSolution<T>>, SearchSpace<T>>() {
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.getAttribute<Stash<BasicSolution<T>>>().addToStash(memory.currentSolution)
    }

    override fun toString(): String = "PushStash"
}