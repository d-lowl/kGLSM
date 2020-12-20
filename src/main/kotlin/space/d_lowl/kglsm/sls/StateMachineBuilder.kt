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

    private var stateMap: MutableMap<String, StateMachineNode<T, U>> = StateMachine.getDefaultStateMapping<T, U>().toMutableMap()

    /**
     * Add strategy to the state machine
     *
     * @param[strategy] Strategy to use as a state
     */
    fun addNode(name: String, strategy: Strategy<T, U>): StateMachineBuilder<T, U> {
        if (name in stateMap.keys) throw Exception("State Machine Node $name already exists")
        stateMap[name] = StateMachineNode(strategy)
        return this
    }

    /**
     * Add transition to the state machine
     *
     * @param[transition] State transition
     */
    fun addTransition(from: String, transition: StateMachineTransition<Memory<T, U>>): StateMachineBuilder<T, U> {
        if (from in stateMap)
            stateMap[from]!!.transitions.add(transition)
        else
            throw Exception("$from state does not exist")
        return this
    }

    /**
     * Build state machine
     */
    open fun build(startingNode: String): StateMachine<T, U> {
        if (stateMap[StateMachine.TERMINATION_STATE_LABEL]!!.transitions.size > 0) {
            logger.error { "Termination must not have outgoing transitions" }
            throw Exception("Termination must not have outgoing transitions")
        }
        val notConnected = checkNotConnected(startingNode, stateMap)
        if (notConnected.contains(StateMachine.TERMINATION_STATE_LABEL)) {
            logger.error { "No path leads to termination" }
            throw Exception("No path leads to termination")
        }
        if (notConnected.isNotEmpty()) {
            logger.warn { "Some states are unreachable:\n${notConnected.map { stateMap[it]!!.strategy }.joinToString(", ")}" }
        }
        return StateMachine(stateMap, startingNode)
    }

    companion object {
        private val logger = KotlinLogging.logger {}

        private fun <T, U> checkNotConnected(start: String, stateMap: Map<String, StateMachineNode<T, U>>): List<String> {
            fun <E> MutableList<E>.pop(): E {
                val res = this.first()
                this.removeAt(0)
                return res
            }
            val stateIndices = stateMap.keys.toMutableList()
            stateIndices.remove(start)
            val queue = mutableListOf(start)
            while (queue.isNotEmpty()) {
                val current = queue.pop()
                val neighbours = (stateMap[current] ?: throw Exception("$current node does not exist")).transitions.map { it.to }
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