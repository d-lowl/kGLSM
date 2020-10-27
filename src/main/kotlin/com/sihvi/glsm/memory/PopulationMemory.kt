package com.sihvi.glsm.memory

typealias PopulationSolution<T> = List<BasicSolution<T>>

class PopulationCurrentState<T>(override var currentSolution: PopulationSolution<T>) : CurrentState<T, PopulationSolution<T>> {
    override fun getCurrentBest(): BasicSolution<T> = currentSolution.minBy { it.cost }!!
}

class PopulationMemory<T>(initialSolution: PopulationSolution<T>) : Memory<T, PopulationSolution<T>>(
        PopulationCurrentState(initialSolution),
        PopulationCurrentState(initialSolution).getCurrentBest()
)