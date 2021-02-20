package space.d_lowl.kglsm.sls

import mu.KotlinLogging
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * State Machine Transition
 *
 * @param[M] Memory type
 * @param[to] State to transit to
 * @param[transitionPredicate] Transition predicate
 * @param[priority] Priority of transition if multiple predicates resolve to true
 */
data class StateMachineTransition<M: Memory<*, *>>(
        val to: String,
        val transitionPredicate: TransitionPredicate<M>,
        val priority: Int = 0
) {
    private constructor(builder: Builder<M>) : this(builder.to!!, builder.transitionPredicate!!, builder.priority)

    fun toString(from: String): String = "$from -> $to [label=\"$transitionPredicate\"]"

    class Builder<M : Memory<*, *>> {
        var to: String? = null
            internal set
        var transitionPredicate: TransitionPredicate<M>? = null
            internal set
        var priority: Int = 0
            internal set

        fun build(): StateMachineTransition<M> {
            checkNotNull(to)
            checkNotNull(transitionPredicate)
            return StateMachineTransition(this)
        }
    }
}

/**
 * State Machine Node
 *
 *
 */
data class StateMachineNode<T, U>(
        val strategy: Strategy<T, U>?,
        val transitions: ArrayList<StateMachineTransition<Memory<T, U>>> = arrayListOf()
) {
    private constructor(builder: Builder<T, U>) : this(builder.strategy, builder.transitions)

    class Builder<T, U> {
        var name: String? = null
        var strategy: Strategy<T, U>? = null
        var transitions: ArrayList<StateMachineTransition<Memory<T, U>>> = arrayListOf()
            private set

        inline fun transition(initTransition: StateMachineTransition.Builder<Memory<T, U>>.() -> Unit): StateMachineTransition<Memory<T, U>> {
            val builder = StateMachineTransition.Builder<Memory<T, U>>()
            builder.initTransition()
            val transition = builder.build()
            transitions.add(transition)
            return transition
        }

        fun build(): StateMachineNode<T, U> {
            checkNotNull(name)
            return StateMachineNode(this)
        }
    }
}

/**
 * GLSM State Machine
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @param[states] Map of states of a state machine by their labels
 * @param[entrypoint] Label of an entrypoint
 */
class StateMachine<T, U>(
        private val states: Map<String, StateMachineNode<T, U>>,
        entrypoint: String
) {

    private constructor(builder: Builder<T, U>) : this(builder.states.toMap(), builder.entrypoint!!)

    private var currentStateName = entrypoint

    /**
     * Perform a transition according to the current state and [memory]
     *
     * @param[memory] Memory
     */
    fun transition(memory: Memory<T, U>) {
        memory.updateRandomVariable()
        val candidateTransitions = currentState?.transitions?.filter { it.transitionPredicate.isSatisfied(memory) }

        if (candidateTransitions != null && candidateTransitions.isNotEmpty()) {
            currentStateName = candidateTransitions.maxBy { it.priority }?.to ?: currentStateName
        }
    }

    /**
     * Check if the state machine is in the termination state
     */
    fun isFinished(): Boolean = currentStateName == TERMINATION_STATE_LABEL

    var currentState: StateMachineNode<T, U>? = null
        get() = states[currentStateName]
        private set

    var strategy: Strategy<T, U>? = null
        get() = currentState?.strategy
        private set

    override fun toString(): String = ""
//                    "digraph G {\n" +
//                    "S [label=\"Enter\"]\n" +
//                    "-1 [label=\"Terminate\", border=\"double\"]\n " +
//                    strategies
//                            .mapIndexed { index, strategy -> "$index [label=\"$strategy\"]"  }
//                            .joinToString("\n") + "\n" +
//                    transitions.joinToString("\n") +
//                    "\nS -> 0 [label=\"⊤\"]\n" +
//                    "}"

    private fun String.runCommand(input: String): String? {
        try {
            val parts = this.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
                    .redirectError(ProcessBuilder.Redirect.PIPE)
                    .start()

            val writer = proc.outputStream.bufferedWriter()
            writer.write(input)
            writer.close()
            proc.waitFor(2, TimeUnit.MINUTES)
            return proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Returns ASCII representation of this State Machine
     *
     * Requires graph-easy to be installed, check README.md for details
     */
    fun toASCII(): String? {
        val dot = toString()//.replace("\n", " ").replace("\"", "\\\"")
        return "graph-easy --as_ascii".runCommand(dot)
    }

    companion object {
        const val TERMINATION_STATE_LABEL = "TERMINATION_STATE"

        fun <T, U> getDefaultStateMapping(): Map<String, StateMachineNode<T, U>> = mapOf(
                TERMINATION_STATE_LABEL to StateMachineNode(null)
        )
    }

    class Builder<T, U> {
        var states: MutableMap<String, StateMachineNode<T, U>> = getDefaultStateMapping<T, U>().toMutableMap()
            private set
        var entrypoint: String? = null
            internal set

        fun build(): StateMachine<T, U> {
            checkNotNull(entrypoint)
            if (states[TERMINATION_STATE_LABEL]!!.transitions.size > 0) {
                logger.error { "Termination must not have outgoing transitions" }
                throw Exception("Termination must not have outgoing transitions")
            }
            val notConnected = checkNotConnected(entrypoint!!, states)
            if (notConnected.contains(TERMINATION_STATE_LABEL)) {
                logger.error { "No path leads to termination" }
                throw Exception("No path leads to termination")
            }
            if (notConnected.isNotEmpty()) {
                logger.warn { "Some states are unreachable:\n${notConnected.map { states[it]!!.strategy }.joinToString(", ")}" }
            }
            return StateMachine(this)
        }

        inline fun node(initNode: StateMachineNode.Builder<T, U>.() -> Unit): StateMachineNode<T, U> {
            val builder = StateMachineNode.Builder<T, U>()
            builder.initNode()
            val node = builder.build()
            val name = builder.name!!
            if (name == TERMINATION_STATE_LABEL) throw Exception("$TERMINATION_STATE_LABEL label is reserved")
            if (name in states.keys) throw Exception("State Machine Node $name already exists")
            states[name] = node
            return node
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
                    val neighbours = (stateMap[current]
                            ?: throw Exception("$current node does not exist")).transitions.map { it.to }
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
}

inline fun <T, U> stateMachine(initStateMachine: StateMachine.Builder<T, U>.() -> Unit): StateMachine<T, U> {
    val builder = StateMachine.Builder<T, U>()
    builder.initStateMachine()
    return builder.build()
}