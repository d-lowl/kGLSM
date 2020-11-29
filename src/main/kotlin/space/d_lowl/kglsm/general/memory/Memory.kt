package space.d_lowl.kglsm.general.memory

import space.d_lowl.kglsm.general.memory.attribute.MemoryAttribute
import mu.KotlinLogging
import java.lang.Exception
import kotlin.random.Random

/**
 * Current state interface
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @property[currentSolution] Current solution hold by the state
 */
interface CurrentState<T, U> {
    var currentSolution: U

    /**
     * Get the best solution of the current state
     */
    fun getCurrentBest(): BasicSolution<T>
}

/**
 * GLSM Memory
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @param[currentState] Initial state
 * @param[initialSolution] Solution to initialise with
 * @param[memoryAttributes] Memory attributes
 * @property[memoryAttributes] Memory attributes
 * @property[bestSolution] The best solution that was saved by memory
 * @property[stepCount] Number of steps performed by GLSM
 * @property[noImprovementCount] Number of steps performed without improvement
 * @property[randomVariable] Random variable to be used for probabilistic actions
 */
open class Memory<T, U>(private val currentState: CurrentState<T, U>, initialSolution: BasicSolution<T>, val memoryAttributes: List<MemoryAttribute>) : CurrentState<T, U> by currentState {
    var bestSolution: BasicSolution<T> = initialSolution
    open var stepCount: Int = 0
    open var noImprovementCount: Int = 0
    var randomVariable: Double = 0.0
        private set

    /**
     * Update current memory state
     *
     * @param[solution] Solution to update with
     */
    fun update(solution: U) {
        currentState.currentSolution = solution
        logger.info { "Current best cost: ${currentState.getCurrentBest().cost}" }
    }

    /**
     * Try updating the best solution
     */
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

    /**
     * Update internal random variable
     */
    fun updateRandomVariable() {
        randomVariable = Random.nextDouble()
    }

    override fun toString(): String =
            "Steps count: $stepCount\n" +
            "No improvement count: $stepCount\n" +
            "Best: $bestSolution\n" +
            "Current state:\n$currentState\n" +
            "Attributes:\n${memoryAttributes.joinToString("\n")}"

    /**
     * Get Memory Attribute
     *
     * @param[A] Subtype of [Memory Attribute][space.d_lowl.kglsm.general.memory.attribute.MemoryAttribute] to get
     * @return [Memory Attribute][space.d_lowl.kglsm.general.memory.attribute.MemoryAttribute] of type [A] if it exists
     * @exception <Exception> if the requested memory attribute does not exist
     */
    inline fun <reified A> getAttribute(): A =
            memoryAttributes.find { it is A } as A? ?: throw Exception("The requested memory attribute does not exist")

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}

