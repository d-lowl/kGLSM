package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate

/**
 *
 *
 * @param[T] Solution entity type
 * @param[S] Solution type
 * @param[M] Memory type
 */
class GLSM<T, S, M: IMemory<S>>(
        private val memory: M,
        private val strategy: Strategy<T, M>,
        private val terminationPredicate: TransitionPredicate<M>
) {
    fun solve(): S {
        while (!terminationPredicate.isTerminate(memory)) {
            strategy.step(memory)
        }
        return memory.getBestSolution()
    }
}