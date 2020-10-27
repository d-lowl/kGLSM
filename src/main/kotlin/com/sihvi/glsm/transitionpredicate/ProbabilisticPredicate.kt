package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.IMemory
import kotlin.random.Random

class ProbabilisticPredicate<T : IMemory<*, *>>(
        private val from : Double = 0.0,
        private val to : Double = 1.0
) : TransitionPredicate<T> {
    override fun isSatisfied(memory: T): Boolean = Random.nextDouble() in from..to
}