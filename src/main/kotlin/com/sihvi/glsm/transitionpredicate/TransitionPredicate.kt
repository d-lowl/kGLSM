package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.IMemory

interface TransitionPredicate<in T : IMemory<*, *>> {
    fun isSatisfied(memory: T): Boolean
}