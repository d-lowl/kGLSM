package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * State Machine Transition
 *
 * @param[M] Memory type
 * @param[from] State to transit from
 * @param[to] State to transit to
 * @param[transitionPredicate] Transition predicate
 */
data class StateMachineTransition<M: Memory<*, *>>(
        val to: String,
        val transitionPredicate: TransitionPredicate<M>,
        val priority: Int = 0
) {
    fun toString(from: String): String = "$from -> $to [label=\"$transitionPredicate\"]"
}

/**
 * State Machine Node
 *
 *
 */
data class StateMachineNode<T, U>(
        val strategy: Strategy<T, U>?,
        val transitions: ArrayList<StateMachineTransition<Memory<T, U>>> = arrayListOf()
)

/**
 * GLSM State Machine
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @param[strategies] Array of strategies to be used as states
 * @param[transitions] List of transitions
 */
class StateMachine<T, U>(
        private val states: Map<String, StateMachineNode<T, U>>,
        private val entrypoint: String
) {
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
}