package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.IMemory

interface SLSBuilder<T, S, M: IMemory<S>> {
    fun build(): GLSM<T, S, M>
}