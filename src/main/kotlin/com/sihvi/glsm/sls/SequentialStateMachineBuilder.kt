package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.NotPredicate
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import com.sihvi.glsm.transitionpredicate.UnconditionalPredicate

class SequentialStateMachineBuilder<T, U>(private val terminationPredicate: TransitionPredicate<Memory<T, U>>): StateMachineBuilder<T, U>() {
    private var steps = 0
    private val unconditionalPredicate = UnconditionalPredicate()
    fun addStep(strategy: Strategy<T, U>): SequentialStateMachineBuilder<T, U> {
        this.addStrategy(strategy)
        if (steps > 0) this.addTransition(StateMachineTransition(steps - 1, steps, unconditionalPredicate))
        steps++
        return this
    }

    override fun build(): StateMachine<T, U> {
        this.addTransition(StateMachineTransition(steps - 1, -1, terminationPredicate))
        this.addTransition(StateMachineTransition(steps - 1, 0, NotPredicate(terminationPredicate)))
        return super.build()
    }
}