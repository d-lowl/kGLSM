package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.IMemory

class NotPredicate<T : IMemory<*>>(private val predicate: TransitionPredicate<T>) : TransitionPredicate<T> {
    override fun isSatisified(memory: T): Boolean = !predicate.isSatisified(memory)
}