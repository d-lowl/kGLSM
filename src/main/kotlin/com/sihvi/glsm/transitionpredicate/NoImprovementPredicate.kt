package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

class NoImprovementPredicate(private val maxIterations: Int): TransitionPredicate<Memory<*, *>>
{
    override fun isSatisfied(memory: Memory<*, *>): Boolean = memory.noImprovementCount >= maxIterations
}