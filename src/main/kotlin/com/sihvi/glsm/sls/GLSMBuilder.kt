package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy

open class GLSMBuilder<T, S, M: Memory<T, S>, N: SearchSpace<T>> {
    private val stateMachineBuilder = StateMachineBuilder<T, S, M, N>()
    fun addStrategy(strategy: Strategy<T, M, N>): GLSMBuilder<T, S, M, N> {
        stateMachineBuilder.addStrategy(strategy)
        return this
    }

    fun addTransition(transition: StateMachineTransition<M>): GLSMBuilder<T, S, M, N> {
        stateMachineBuilder.addTransition(transition)
        return this
    }

    open fun build(): GLSM<T, S, M, N> {
        val stateMachine = stateMachineBuilder.build()
        return GLSM(stateMachine)
    }
}