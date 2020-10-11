package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

class FixedIterationPredicate<T: Memory<*>>(private val maxIterations: Int): TransitionPredicate<T> {
    override fun isTerminate(memory: T): Boolean = memory.stepCount >= maxIterations
}