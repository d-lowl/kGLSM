package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace

abstract class Strategy<T>(private val space: DiscreteSearchSpace<T>, private val problem: Problem<T>, private val memory: Memory<T>) {
    abstract fun step()
}