package com.sihvi.glsm.memory

import com.sihvi.glsm.memory.attribute.MemoryAttribute
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.SearchSpace

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

    override fun toString(): String = "{${solution.joinToString(",")} : $cost}"
}

class BasicCurrentState<T>(override var currentSolution: BasicSolution<T>) : CurrentState<T, BasicSolution<T>> {
    override fun getCurrentBest(): BasicSolution<T> = currentSolution

    override fun toString(): String = currentSolution.toString()

    companion object {
        fun <T> getInitialSolution(searchSpace: SearchSpace<T>, problem: Problem<T>): BasicSolution<T> {
            val solution = searchSpace.getInitial()
            return BasicSolution<T>(solution, problem.evaluate(solution))
        }
    }
}

class BasicMemory<T>(initialSolution: BasicSolution<T>, memoryAttributes: List<MemoryAttribute> = listOf()) : Memory<T, BasicSolution<T>>(BasicCurrentState(initialSolution), initialSolution, memoryAttributes)