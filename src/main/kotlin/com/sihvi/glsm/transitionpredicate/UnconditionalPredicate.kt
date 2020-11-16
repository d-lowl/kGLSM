package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

class UnconditionalPredicate: TransitionPredicate<Memory<*, *>> {
    override fun isSatisfied(memory: Memory<*, *>): Boolean = true
    override fun toString(): String = "⊤"
}