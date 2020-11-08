package com.sihvi.glsm.problem

typealias CostFunction<T> = (Array<T>) -> Double

/**
 * Interface defining the minimal set of methods for a problem class
 *
 * @param[T] Solution entity type
 */
interface Problem<T> {
    fun evaluate(solution: Array<T>): Double
}