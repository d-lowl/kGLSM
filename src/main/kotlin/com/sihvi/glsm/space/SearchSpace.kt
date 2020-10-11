package com.sihvi.glsm.space

interface SearchSpace<T> {
    fun getInitial(): Array<T>
}

interface DiscreteSearchSpace<T> : SearchSpace<T> {
    fun getNeighbourhood(solution: Array<T>): Sequence<Array<T>>
    fun getRandomNeighbour(solution: Array<T>): Array<T>
}