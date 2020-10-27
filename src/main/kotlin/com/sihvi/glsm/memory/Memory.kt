package com.sihvi.glsm.memory

/**
 * Interface defining minimal set of methods for GLSM memory
 *
 * @param[T]
 * @param[U] Solution type
 */
interface IMemory<T, U> {
    var bestSolution: BasicSolution<T>
    fun update(solution: U)
    fun updateBest()
    var stepCount: Int
    var noImprovementCount: Int
}

interface CurrentState<T, U> {
    var currentSolution: U
    fun getCurrentBest(): BasicSolution<T>
}

open class Memory<T, U>(private val currentState: CurrentState<T, U>, initialSolution: BasicSolution<T>) : IMemory<T, U>, CurrentState<T, U> by currentState {
    override var bestSolution: BasicSolution<T> = initialSolution
    override var stepCount: Int = 0
    override var noImprovementCount: Int = 0

    override fun update(solution: U) {
        currentState.currentSolution = solution
        stepCount++
    }

    override fun updateBest() {
        if (currentState.getCurrentBest().cost < bestSolution.cost) {
            bestSolution = currentState.getCurrentBest()
            noImprovementCount = 0
        } else {
            noImprovementCount++
        }
    }


}

