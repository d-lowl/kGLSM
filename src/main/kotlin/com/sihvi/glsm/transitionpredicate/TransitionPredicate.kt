package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

interface TransitionPredicate<in T : Memory<*, *>> {
    fun isSatisfied(memory: T): Boolean
}