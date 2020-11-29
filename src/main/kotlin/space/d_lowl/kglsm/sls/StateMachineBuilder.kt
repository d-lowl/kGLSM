package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.strategy.Strategy
import mu.KotlinLogging

/**
 * State Machine Builder
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 */
open class StateMachineBuilder<T, U> {

    private var strategies = mutableListOf<Strategy<T, U>>()
    private var transitions = mutableListOf<StateMachineTransition<Memory<T, U>>>()

    /**
     * Add strategy to the state machine
     *
     * @param[strategy] Strategy to use as a state
     */
    fun addStrategy(strategy: Strategy<T, U>): StateMachineBuilder<T, U> {
        strategies.add(strategy)
        return this
    }

    /**
     * Add transition to the state machine
     *
     * @param[transition] State transition
     */
    fun addTransition(transition: StateMachineTransition<Memory<T, U>>): StateMachineBuilder<T, U> {
        transitions.add(transition)
        return this
    }

    /**
     * Build state machine
     */
    open fun build(): StateMachine<T, U> {
        if (transitions.any { it.from == -1 }) {
            logger.error { "Termination must not have outgoing transitions" }
            throw Exception("Termination must not have outgoing transitions")
        }
        val notConnected = checkNotConnected(transitions, strategies.size)
        if (notConnected.contains(-1)) {
            logger.error { "No path leads to termination" }
            throw Exception("No path leads to termination")
        }
        if (notConnected.isNotEmpty()) {
            logger.warn { "Some states are unreachable:\n${notConnected.map { strategies[it] }.joinToString(", ")}" }
        }
        return StateMachine(strategies.toTypedArray(), transitions.toList())
    }

    companion object {
        private val logger = KotlinLogging.logger {}

        private fun checkNotConnected(transitions: List<StateMachineTransition<*>>, size: Int): List<Int> {
            fun <E> MutableList<E>.pop(): E {
                val res = this.first()
                this.removeAt(0)
                return res
            }
            val stateIndices = (-1 until size).toMutableList()
            stateIndices.remove(0)
            val queue = mutableListOf(0)
            while (queue.isNotEmpty()) {
                val current = queue.pop()
                val neighbours = transitions.filter { it.from == current }.map { it.to }
                neighbours.forEach {
                    if (stateIndices.contains(it)) {
                        stateIndices.remove(it)
                        queue.add(it)
                    }
                }
            }
            return stateIndices.toList()
        }
    }
}