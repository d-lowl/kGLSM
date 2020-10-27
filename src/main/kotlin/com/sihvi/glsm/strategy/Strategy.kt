package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.IMemory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.DiscreteSearchSpace
import com.sihvi.glsm.space.SearchSpace

/**
 * Abstract class of a search strategy
 *
 * @param[T] Type of solution entity
 * @param[M] Memory type
 */
abstract class Strategy<T, M: IMemory<*, *>, S: SearchSpace<T>> {
    abstract fun step(memory: M, space: S, problem: Problem<T>)
}