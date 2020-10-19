package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.strategy.Strategy

class StateMachineBuilder<T, S, M: IMemory<S>> {

    private var strategies = mutableListOf<Strategy<T, M>>()
    private var transitions = mutableListOf<StateMachineTransition<M>>()

    fun addStrategy(strategy: Strategy<T, M>): StateMachineBuilder<T, S, M> {
        strategies.add(strategy)
        return this
    }

    fun addTransition(transition: StateMachineTransition<M>): StateMachineBuilder<T, S, M> {
        transitions.add(transition)
        return this
    }

    fun build(): StateMachine<T, S, M> {
        // TODO check if the transition graph is connected
        return StateMachine(strategies.toTypedArray(), transitions.toList())
    }
}