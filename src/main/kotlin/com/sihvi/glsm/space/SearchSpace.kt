package com.sihvi.glsm.space

/**
 * Search space
 *
 * @param[T] Solution entity type
 */
interface SearchSpace<T> {
    fun getInitial(): Array<T>
    fun getNeighbourhood(solution: Array<T>): Sequence<Array<T>>
    fun getRandomNeighbour(solution: Array<T>): Array<T>
}