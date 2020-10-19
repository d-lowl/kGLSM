package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import kotlin.random.Random

data class StateMachineTransition<M: IMemory<*>>(
        val from: Int,
        val to: Int,
        val transitionPredicate: TransitionPredicate<M>
)

class StateMachine<T, S, M: IMemory<S>>(
        private val strategies: Array<Strategy<T, M>>,
        private val transitions: List<StateMachineTransition<M>>
) {
    private var currentState = 0

    fun transition(memory: M) {
        val candidateTransitions = transitions
                .filter { it.from == currentState }
                .filter { it.transitionPredicate.isTerminate(memory) }
                .map { it.to }
        if (candidateTransitions.isNotEmpty()) {
            currentState = candidateTransitions[Random.nextInt(candidateTransitions.size)]
        }
    }

    fun isFinished(): Boolean = currentState == -1

    var strategy: Strategy<T, M>? = null
        get() = strategies.getOrNull(currentState)
        private set
}