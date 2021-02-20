package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.general.transitionpredicate.NotPredicate
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate
import space.d_lowl.kglsm.general.transitionpredicate.UnconditionalPredicate

class SequentialStep<T, U>(val name: String, val strategy: Strategy<T, U>) {
    private constructor(builder: Builder<T, U>) : this(builder.name!!, builder.strategy!!)

    class Builder<T, U> {
        var name: String? = null
        var strategy: Strategy<T, U>? = null

        fun build(): SequentialStep<T, U> {
            checkNotNull(name)
            checkNotNull(strategy)
            return SequentialStep(this)
        }
    }
}

/**
 * Sequential State Machine builder
 *
 * Builds a sequential state machine, i.e. a state machine that consists of a sequence of states that are executed
 * in a loop until the [termination predicate][terminationPredicate] is met at the end of the sequence
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 */
class SequentialStateMachineBuilder<T, U> {
    var steps: MutableList<SequentialStep<T, U>> = mutableListOf()
    var terminationPredicate: TransitionPredicate<Memory<T, U>>? = null
    private val unconditionalPredicate = UnconditionalPredicate()

    inline fun step(initStep: SequentialStep.Builder<T, U>.() -> Unit): SequentialStep<T, U> {
        val builder = SequentialStep.Builder<T, U>()
        builder.initStep()
        val step = builder.build()
        steps.add(step)
        return step
    }

    /**
     * Build Sequential State Machine
     */
    fun build(): StateMachine<T, U> {
        if (steps.size == 0) throw Exception("No nodes have been added")
        if (terminationPredicate == null) throw Exception("No termination predicate specified")

        return stateMachine<T, U> {
            entrypoint = steps.first().name
            steps.zipWithNext().forEach { pair ->
                val current = pair.first
                val next = pair.second
                node {
                    name = current.name
                    strategy = current.strategy
                    transition {
                        name = next.name
                        transitionPredicate = unconditionalPredicate
                    }
                }
            }
            node {
                name = steps.last().name
                strategy = steps.last().strategy
                transition {
                    name = steps.first().name
                    transitionPredicate = NotPredicate(terminationPredicate!!)
                }
                transition {
                    name = StateMachine.TERMINATION_STATE_LABEL
                    transitionPredicate = terminationPredicate!!
                }
            }
        }
    }
}

inline fun <T, U> sequentialStateMachine(initStateMachine: SequentialStateMachineBuilder<T, U>.() -> Unit): StateMachine<T, U> {
    val builder = SequentialStateMachineBuilder<T, U>()
    builder.initStateMachine()
    return builder.build()
}