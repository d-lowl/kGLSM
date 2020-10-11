package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate

class GLSM<T, U: Memory<T>>(
        private val memory: U,
        private val strategy: Strategy<T>,
        private val terminationPredicate: TransitionPredicate<U>
) {
    fun solve(): Pair<Array<T>, Double> {
        while (!terminationPredicate.isTerminate(memory)) {
            strategy.step()
        }
        return Pair(memory.bestSolution, memory.bestSolutionCost)
    }
}