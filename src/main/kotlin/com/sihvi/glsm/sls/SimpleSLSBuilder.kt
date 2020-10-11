package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import java.lang.Exception

class SimpleSLSBuilder<T, U: Memory<T>>: SLSBuilder<T, U> {
    private var memory: U? = null
    private var strategy: Strategy<T>? = null
    private var terminationPredicate: TransitionPredicate<U>? = null

    fun addMemory(memory: U): SimpleSLSBuilder<T, U> {
        this.memory = memory
        return this
    }

    fun addStrategy(strategy: Strategy<T>): SimpleSLSBuilder<T, U> {
        this.strategy = strategy
        return this
    }

    fun addTerminationPredicate(terminationPredicate: TransitionPredicate<U>): SimpleSLSBuilder<T, U> {
        this.terminationPredicate = terminationPredicate
        return this
    }

    override fun build(): GLSM<T, U> {
        if(memory == null) throw Exception("Memory is not defined")
        if(strategy == null) throw Exception("Strategy is not defined")
        if(terminationPredicate == null) throw Exception("Termination Predicate is not defined")

        return GLSM(memory!!, strategy!!, terminationPredicate!!)
    }
}