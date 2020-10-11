package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

interface TransitionPredicate<T : Memory<*>> {
    fun isTerminate(memory: T): Boolean
}