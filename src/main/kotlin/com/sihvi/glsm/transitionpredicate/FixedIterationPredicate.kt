package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.HasStepCount
import com.sihvi.glsm.memory.IMemory

class FixedIterationPredicate<T>(private val maxIterations: Int): TransitionPredicate<T>
        where T : IMemory<*>, T : HasStepCount
{
    override fun isTerminate(memory: T): Boolean = memory.getStepCount() >= maxIterations
}