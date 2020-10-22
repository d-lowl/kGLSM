package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy

class GLSMBuilder<T, S, M: IMemory<S>, N: SearchSpace<T>> {
    private var memory: M? = null
    fun addMemory(memory: M): GLSMBuilder<T, S, M, N> {
        this.memory = memory
        return this
    }

    private val stateMachineBuilder = StateMachineBuilder<T, S, M, N>()
    fun addStrategy(strategy: Strategy<T, M, N>): GLSMBuilder<T, S, M, N> {
        stateMachineBuilder.addStrategy(strategy)
        return this
    }

    fun addTransition(transition: StateMachineTransition<M>): GLSMBuilder<T, S, M, N> {
        stateMachineBuilder.addTransition(transition)
        return this
    }

    fun build(): GLSM<T, S, M, N> {
        if (memory == null) throw IllegalArgumentException("Memory was not set")
        val stateMachine = stateMachineBuilder.build()
        return GLSM(memory!!, stateMachine)
    }
}