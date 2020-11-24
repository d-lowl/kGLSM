package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

data class StateMachineTransition<M: Memory<*, *>>(
        val from: Int,
        val to: Int,
        val transitionPredicate: TransitionPredicate<M>,
        val priority: Int = 0
) {
    override fun toString(): String = "$from -> $to [label=\"$transitionPredicate\"]"
}

class StateMachine<T, U>(
        private val strategies: Array<Strategy<T, U>>,
        private val transitions: List<StateMachineTransition<Memory<T, U>>>
) {
    private var currentState = 0

    fun transition(memory: Memory<T, U>) {
        memory.updateRandomVariable()
        val candidateTransitions = transitions
                .filter { it.from == currentState }
                .filter { it.transitionPredicate.isSatisfied(memory) }

        if (candidateTransitions.isNotEmpty()) {
            currentState = candidateTransitions.maxBy { it.priority }?.to ?: currentState
        }
    }

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

    fun toASCII(): String? {
        val dot = toString()//.replace("\n", " ").replace("\"", "\\\"")
        return "graph-easy --as_ascii".runCommand(dot)
    }
}