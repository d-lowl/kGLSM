package com.sihvi.glsm.space

/**
 * Search space
 *
 * @param[T] Solution entity type
 */
interface SearchSpace<T> {
    fun getInitial(): Array<T>
}

interface HasDiscreteNeighbourhood<T> {
    fun getNeighbourhood(solution: Array<T>): Sequence<Array<T>>
}

interface HasRandomNeighbour<T> {
    fun getRandomNeighbour(solution: Array<T>): Array<T>
}

interface DiscreteSearchSpace<T> :
    SearchSpace<T>,
    HasDiscreteNeighbourhood<T>,
    HasRandomNeighbour<T>