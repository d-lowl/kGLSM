package com.sihvi.glsm.memory

import com.sihvi.glsm.memory.attribute.MemoryAttribute
import mu.KotlinLogging
import java.lang.Exception
import kotlin.random.Random

interface CurrentState<T, U> {
    var currentSolution: U
    fun getCurrentBest(): BasicSolution<T>
}

open class Memory<T, U>(private val currentState: CurrentState<T, U>, initialSolution: BasicSolution<T>, val memoryAttributes: List<MemoryAttribute>) : CurrentState<T, U> by currentState {
    var bestSolution: BasicSolution<T> = initialSolution
    open var stepCount: Int = 0
    open var noImprovementCount: Int = 0
    var randomVariable: Double = 0.0
        private set

    fun update(solution: U) {
        currentState.currentSolution = solution
        logger.info { "Current best cost: ${currentState.getCurrentBest().cost}" }
    }

    fun updateBest() {
        if (currentState.getCurrentBest().cost < bestSolution.cost) {
            bestSolution = currentState.getCurrentBest()
            noImprovementCount = 0
            logger.info { "Total best cost: ${bestSolution.cost}" }
        } else {
            logger.info { "Total best cost: ${bestSolution.cost} (no improvement)" }

            noImprovementCount++
        }
    }

    fun updateRandomVariable() {
        randomVariable = Random.nextDouble()
    }

    override fun toString(): String =
            "Steps count: $stepCount\n" +
            "No improvement count: $stepCount\n" +
            "Best: $bestSolution\n" +
            "Current state:\n$currentState\n" +
            "Attributes:\n${memoryAttributes.joinToString("\n")}"

    inline fun <reified A> getAttribute(): A =
            memoryAttributes.find { it is A } as A? ?: throw Exception("The requested memory attribute does not exist")

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}

