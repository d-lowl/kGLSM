package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy

class StateMachineBuilder<T, S, M: IMemory<S>, N: SearchSpace<T>> {

    private var strategies = mutableListOf<Strategy<T, M, N>>()
    private var transitions = mutableListOf<StateMachineTransition<M>>()

    fun addStrategy(strategy: Strategy<T, M, N>): StateMachineBuilder<T, S, M, N> {
        strategies.add(strategy)
        return this
    }

    fun addTransition(transition: StateMachineTransition<M>): StateMachineBuilder<T, S, M, N> {
        transitions.add(transition)
        return this
    }

    fun build(): StateMachine<T, S, M, N> {
        // TODO check if the transition graph is connected
        return StateMachine(strategies.toTypedArray(), transitions.toList())
    }
}