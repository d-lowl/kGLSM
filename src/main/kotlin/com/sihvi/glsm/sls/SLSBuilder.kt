package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory

interface SLSBuilder<T, U: Memory<T>> {
    fun build(): GLSM<T, U>
}