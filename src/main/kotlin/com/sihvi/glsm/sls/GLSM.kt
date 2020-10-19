package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate

/**
 * Generalised Local Search Machine
 *
 * @param[T] Solution entity type
 * @param[S] Solution type
 * @param[M] Memory type
 * @param[memory] GLSM memory
 * @param[stateMachine] GLSM strategy state machine
 */
class GLSM<T, S, M: IMemory<S>>(
        private val memory: M,
        private val stateMachine: StateMachine<T, S, M>
) {
    fun solve(): S {
        while (!stateMachine.isFinished()) {
            stateMachine.strategy?.step(memory)
            stateMachine.transition(memory)
        }
        return memory.getBestSolution()
    }

    companion object {
        fun <T, S, M: IMemory<S>> getSingleStrategyGLSM(
                memory: M,
                strategy: Strategy<T, M>,
                terminationPredicate: TransitionPredicate<M>
        ): GLSM<T, S, M> {
            val stateMachine = StateMachineBuilder<T, S, M>()
                    .addStrategy(strategy)
                    .addTransition(StateMachineTransition(0, -1, terminationPredicate))
                    .build()
            return GLSM(memory, stateMachine)
        }
    }
}