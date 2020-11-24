package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.space.SearchSpace

/**
 * Abstract class of a search strategy
 *
 * @param[T] Type of solution entity
 * @param[U] Memory type
 */
abstract class Strategy<T, U> {
    abstract fun step(memory: Memory<T, U>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>)
}