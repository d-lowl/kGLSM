package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.NotPredicate
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import com.sihvi.glsm.transitionpredicate.UnconditionalPredicate

class SequentialStateMachineBuilder<T, S, M: Memory<T, S>, N: SearchSpace<T>>(private val terminationPredicate: TransitionPredicate<M>): StateMachineBuilder<T, S, M, N>() {
    private var steps = 0
    private val unconditionalPredicate = UnconditionalPredicate()
    fun addStep(strategy: Strategy<T, M, N>): SequentialStateMachineBuilder<T, S, M, N> {
        this.addStrategy(strategy)
        if (steps > 0) this.addTransition(StateMachineTransition(steps - 1, steps, unconditionalPredicate))
        steps++
        return this
    }

    override fun build(): StateMachine<T, S, M, N> {
        this.addTransition(StateMachineTransition(steps - 1, -1, terminationPredicate))
        this.addTransition(StateMachineTransition(steps - 1, 0, NotPredicate(terminationPredicate)))
        return super.build()
    }
}