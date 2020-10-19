package com.sihvi.glsm.memory

/**
 * Interface defining minimal set of methods for GLSM memory
 *
 * @param[U] Solution type
 */
interface IMemory<U> {
    fun getBestSolution(): U
    fun getCurrentSolution(): U
    fun update(solution: U)
}

/**
 * Memory trait to have step count
 */
interface HasStepCount {
    fun getStepCount(): Int
}

/**
 * Memory trait to count steps without improvement
 */
interface HasNoImprovementCount {
    fun getNoImprovementCount(): Int
}

