package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.IMemory

class NoImprovementPredicate<T>(private val maxIterations: Int): TransitionPredicate<T>
    where T : IMemory<*, *>
{
    override fun isSatisfied(memory: T): Boolean = memory.noImprovementCount >= maxIterations
}