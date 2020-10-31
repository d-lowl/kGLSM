package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate

data class StateMachineTransition<M: Memory<*, *>>(
        val from: Int,
        val to: Int,
        val transitionPredicate: TransitionPredicate<M>,
        val priority: Int = 0
)

class StateMachine<T, S, M: Memory<T, S>, N: SearchSpace<T>>(
        private val strategies: Array<Strategy<T, M, N>>,
        private val transitions: List<StateMachineTransition<M>>
) {
    private var currentState = 0

    fun transition(memory: M) {
        val candidateTransitions = transitions
                .filter { it.from == currentState }
                .filter { it.transitionPredicate.isSatisfied(memory) }

        if (candidateTransitions.isNotEmpty()) {
            currentState = candidateTransitions.maxBy { it.priority }?.to ?: currentState
        }
    }

    fun isFinished(): Boolean = currentState == -1

    var strategy: Strategy<T, M, N>? = null
        get() = strategies.getOrNull(currentState)
        private set
}