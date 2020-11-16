package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory

class FixedIterationPredicate(private val maxIterations: Int): TransitionPredicate<Memory<*, *>>
{
    override fun isSatisfied(memory: Memory<*, *>): Boolean = memory.stepCount >= maxIterations
    override fun toString(): String = "STEPS >= $maxIterations"
}