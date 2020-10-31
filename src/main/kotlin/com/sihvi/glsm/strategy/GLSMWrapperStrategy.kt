package com.sihvi.glsm.strategy

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.sls.GLSM
import com.sihvi.glsm.space.SearchSpace

class GLSMWrapperStrategy<T, S, M: Memory<T, S>, N: SearchSpace<T>>(private val glsm: GLSM<T, S, M, N>) : Strategy<T, M, N>() {
    override fun step(memory: M, searchSpace: N, problem: Problem<T>) {
        glsm.solve(memory, searchSpace, problem)
    }
}