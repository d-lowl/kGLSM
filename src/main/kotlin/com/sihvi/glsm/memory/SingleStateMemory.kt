package com.sihvi.glsm.memory

/**
 * Data class holds a solution array and a corresponding cost
 *
 * @param[T] Solution entity type
 * @param[solution] Solution array
 * @param[cost] Solution cost
 */
data class BasicSolution<T>(val solution: Array<T>, val cost: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BasicSolution<*>

        if (!solution.contentEquals(other.solution)) return false
        if (cost != other.cost) return false

        return true
    }

    override fun hashCode(): Int {
        var result = solution.contentHashCode()
        result = 31 * result + cost.hashCode()
        return result
    }
}

class BasicCurrentState<T>(override var currentSolution: BasicSolution<T>) : CurrentState<T, BasicSolution<T>> {
    override fun getCurrentBest(): BasicSolution<T> = currentSolution
}

class BasicMemory<T>(initialSolution: BasicSolution<T>) : Memory<T, BasicSolution<T>>(BasicCurrentState(initialSolution), initialSolution)