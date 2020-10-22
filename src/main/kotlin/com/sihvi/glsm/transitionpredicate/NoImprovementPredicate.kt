package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.HasNoImprovementCount
import com.sihvi.glsm.memory.IMemory

class NoImprovementPredicate<T>(private val maxIterations: Int): TransitionPredicate<T>
    where T : IMemory<*>, T : HasNoImprovementCount
{
    override fun isSatisified(memory: T): Boolean = memory.getNoImprovementCount() >= maxIterations
}