package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import java.lang.Exception

class SimpleSLSBuilder<T, S, M: IMemory<S>>: SLSBuilder<T, S, M> {
    private var memory: M? = null
    private var strategy: Strategy<T, M>? = null
    private var terminationPredicate: TransitionPredicate<M>? = null

    fun addMemory(memory: M): SimpleSLSBuilder<T, S, M> {
        this.memory = memory
        return this
    }

    fun addStrategy(strategy: Strategy<T, M>): SimpleSLSBuilder<T, S, M> {
        this.strategy = strategy
        return this
    }

    fun addTerminationPredicate(terminationPredicate: TransitionPredicate<M>): SimpleSLSBuilder<T, S, M> {
        this.terminationPredicate = terminationPredicate
        return this
    }

    override fun build(): GLSM<T, S, M> {
        if(memory == null) throw Exception("Memory not provided")
        if(strategy == null) throw Exception("Strategy not provided")
        if(terminationPredicate == null) throw Exception("Termination Predicate not provided")

        return GLSM(memory!!, strategy!!, terminationPredicate!!)
    }
}