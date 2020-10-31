package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace
import com.sihvi.glsm.space.SearchSpace

/**
 * Abstract class of a search strategy
 *
 * @param[T] Type of solution entity
 * @param[M] Memory type
 */
abstract class Strategy<T, M: Memory<*, *>, S: SearchSpace<T>> {
    abstract fun step(memory: M, searchSpace: S, problem: Problem<T>)
}