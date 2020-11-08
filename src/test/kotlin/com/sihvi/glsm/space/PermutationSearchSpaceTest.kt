package com.sihvi.glsm.space

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

internal class PermutationSearchSpaceTest {
    private val searchSpace = PermutationSearchSpace(5)
    private val initialSolution = searchSpace.getInitial()
    private val expectedNeighbourhood = listOf(
            arrayOf(1, 0, 2, 3, 4),
            arrayOf(2, 1, 0, 3, 4),
            arrayOf(3, 2, 1, 0, 4),
            arrayOf(4, 3, 2, 1, 0),
            arrayOf(0, 2, 1, 3, 4),
            arrayOf(0, 3, 2, 1, 4),
            arrayOf(0, 4, 3, 2, 1),
            arrayOf(0, 1, 3, 2, 4),
            arrayOf(0, 1, 4, 3, 2),
            arrayOf(0, 1, 2, 4, 3)
    )

    @Test
    fun getInitial() {
        assert(searchSpace.getInitial().contentEquals(arrayOf(0, 1, 2, 3, 4)))
    }

    @Test
    fun getRandomNeighbour() {
        val neighbour = searchSpace.getRandomNeighbour(initialSolution)
        assert(expectedNeighbourhood.any { it.contentEquals(neighbour) })
    }

    @Test
    fun getNeighbourhood() {
        val neighbourhood = searchSpace.getNeighbourhood(initialSolution)
        assert(expectedNeighbourhood.all { expected ->
            neighbourhood.any { it.contentEquals(expected) }
        })
    }
}