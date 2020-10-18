package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace

/**
 * Abstract class of a search strategy
 *
 * @param[T] Type of solution entity
 * @param[M] Memory type
 * @param[space] Search space
 * @param[problem] Problem instance
 */
abstract class Strategy<T, M: IMemory<*>>(private val space: DiscreteSearchSpace<T>, private val problem: Problem<T>) {
    abstract fun step(memory: M)
}