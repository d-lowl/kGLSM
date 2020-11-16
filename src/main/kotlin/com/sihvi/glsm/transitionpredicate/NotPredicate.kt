package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

class NotPredicate<T : Memory<*, *>>(private val predicate: TransitionPredicate<T>) : TransitionPredicate<T> {
    override fun isSatisfied(memory: T): Boolean = !predicate.isSatisfied(memory)
    override fun toString(): String = "¬($predicate)"
}