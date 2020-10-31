package com.sihvi.glsm.memory

import com.sihvi.glsm.memory.attribute.MemoryAttribute
import java.lang.Exception

interface CurrentState<T, U> {
    var currentSolution: U
    fun getCurrentBest(): BasicSolution<T>
}

open class Memory<T, U>(private val currentState: CurrentState<T, U>, initialSolution: BasicSolution<T>, val memoryAttributes: List<MemoryAttribute>) : CurrentState<T, U> by currentState {
    var bestSolution: BasicSolution<T> = initialSolution
    open var stepCount: Int = 0
    open var noImprovementCount: Int = 0

    fun update(solution: U) {
        currentState.currentSolution = solution
        stepCount++
    }

    fun updateBest() {
        if (currentState.getCurrentBest().cost < bestSolution.cost) {
            bestSolution = currentState.getCurrentBest()
            noImprovementCount = 0
        } else {
            noImprovementCount++
        }
    }

    inline fun <reified A> getAttribute(): A =
            memoryAttributes.find { it is A } as A? ?: throw Exception("The requested memory attribute does not exist")
}

