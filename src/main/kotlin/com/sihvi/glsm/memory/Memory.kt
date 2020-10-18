package com.sihvi.glsm.memory

/**
 * Interface defining minimal set of methods for GLSM memory
 *
 * @param[U] Solution type
 */
interface IMemory<U> {
    fun getBestSolution(): U
    fun getCurrentSolution(): U
    fun update(data: U)
}

interface HasStepCount {
    fun getStepCount(): Int
}

interface HasNoImprovementCount {
    fun getNoImprovementCount(): Int
}

