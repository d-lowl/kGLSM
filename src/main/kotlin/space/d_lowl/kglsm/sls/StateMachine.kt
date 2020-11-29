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
        val from: Int,
        val to: Int,
        val transitionPredicate: TransitionPredicate<M>,
        val priority: Int = 0
) {
    override fun toString(): String = "$from -> $to [label=\"$transitionPredicate\"]"
}

/**
 * GLSM State Machine
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @param[strategies] Array of strategies to be used as states
 * @param[transitions] List of transitions
 */
class StateMachine<T, U>(
    private val strategies: Array<Strategy<T, U>>,
    private val transitions: List<StateMachineTransition<Memory<T, U>>>
) {
    private var currentState = 0

    /**
     * Perform a transition according to the current state and [memory]
     *
     * @param[memory] Memory
     */
    fun transition(memory: Memory<T, U>) {
        memory.updateRandomVariable()
        val candidateTransitions = transitions
                .filter { it.from == currentState }
                .filter { it.transitionPredicate.isSatisfied(memory) }

        if (candidateTransitions.isNotEmpty()) {
            currentState = candidateTransitions.maxBy { it.priority }?.to ?: currentState
        }
    }

    /**
     * Check if the state machine is in the termination state
     */
    fun isFinished(): Boolean = currentState == -1

    var strategy: Strategy<T, U>? = null
        get() = strategies.getOrNull(currentState)
        private set

    override fun toString(): String =
                    "digraph G {\n" +
                    "S [label=\"Enter\"]\n" +
                    "-1 [label=\"Terminate\", border=\"double\"]\n " +
                    strategies
                            .mapIndexed { index, strategy -> "$index [label=\"$strategy\"]"  }
                            .joinToString("\n") + "\n" +
                    transitions.joinToString("\n") +
                    "\nS -> 0 [label=\"⊤\"]\n" +
                    "}"

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
}