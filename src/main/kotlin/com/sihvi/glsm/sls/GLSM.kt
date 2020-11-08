package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate

/**
 * Generalised Local Search Machine
 *
 * @param[T] Solution entity type
 * @param[S] Solution type
 * @param[M] Memory type
 * @param[N] Search space type
 * @param[stateMachine] GLSM strategy state machine
 */
open class GLSM<T, S, M: Memory<T, S>, N: SearchSpace<T>>(
        private val stateMachine: StateMachine<T, S, M, N>
) {
    fun solve(memory: M, space: N, costFunction: CostFunction<T>): BasicSolution<T> {
        while (!stateMachine.isFinished()) {
            stateMachine.strategy?.step(memory, space, costFunction)
            stateMachine.transition(memory)
        }
        return memory.bestSolution
    }

    companion object {
        fun <T, S, M: Memory<T, S>, N: SearchSpace<T>> getSingleStrategyGLSM(
                strategy: Strategy<T, M, N>,
                terminationPredicate: TransitionPredicate<M>
        ): GLSM<T, S, M, N> {
            val stateMachine = StateMachineBuilder<T, S, M, N>()
                    .addStrategy(strategy)
                    .addTransition(StateMachineTransition(0, -1, terminationPredicate))
                    .build()
            return GLSM(stateMachine)
        }
    }
}