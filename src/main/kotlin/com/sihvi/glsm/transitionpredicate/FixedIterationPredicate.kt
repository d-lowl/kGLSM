package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.IMemory

class FixedIterationPredicate<T>(private val maxIterations: Int): TransitionPredicate<T>
        where T : IMemory<*, *>
{
    override fun isSatisfied(memory: T): Boolean = memory.stepCount >= maxIterations
}