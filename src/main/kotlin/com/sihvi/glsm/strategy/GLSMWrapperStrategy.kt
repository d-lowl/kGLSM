package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.sls.GLSM
import com.sihvi.glsm.space.SearchSpace

class GLSMWrapperStrategy<T, U>(private val glsm: GLSM<T, U>) : Strategy<T, U>() {
    override fun step(memory: Memory<T, U>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        glsm.solve(memory, searchSpace, costFunction)
    }
}