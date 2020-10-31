package com.sihvi.glsm.transitionpredicate

import com.sihvi.glsm.memory.Memory
import kotlin.random.Random

class ProbabilisticPredicate(
        private val from : Double = 0.0,
        private val to : Double = 1.0
) : TransitionPredicate<Memory<*, *>> {
    override fun isSatisfied(memory: Memory<*, *>): Boolean = Random.nextDouble() in from..to
}