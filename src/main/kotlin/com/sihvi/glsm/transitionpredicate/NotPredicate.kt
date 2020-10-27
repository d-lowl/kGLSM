package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.IMemory

class NotPredicate<T : IMemory<*, *>>(private val predicate: TransitionPredicate<T>) : TransitionPredicate<T> {
    override fun isSatisfied(memory: T): Boolean = !predicate.isSatisfied(memory)
}