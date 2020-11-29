package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.general.transitionpredicate.NotPredicate
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate
import space.d_lowl.kglsm.general.transitionpredicate.UnconditionalPredicate

/**
 * Sequential State Machine builder
 *
 * Builds a sequential state machine, i.e. a state machine that consists of a sequence of states that are executed
 * in a loop until the [termination predicate][terminationPredicate] is met at the end of the sequence
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @param[terminationPredicate] Termination predicate
 */
class SequentialStateMachineBuilder<T, U>(private val terminationPredicate: TransitionPredicate<Memory<T, U>>): StateMachineBuilder<T, U>() {
    private var steps = 0
    private val unconditionalPredicate = UnconditionalPredicate()

    /**
     * Add a step to the sequence
     *
     * @param[strategy] Strategy to use as a step
     */
    fun addStep(strategy: Strategy<T, U>): SequentialStateMachineBuilder<T, U> {
        this.addStrategy(strategy)
        if (steps > 0) this.addTransition(StateMachineTransition(steps - 1, steps, unconditionalPredicate))
        steps++
        return this
    }

    /**
     * Build Sequential State Machine
     */
    override fun build(): StateMachine<T, U> {
        this.addTransition(StateMachineTransition(steps - 1, -1, terminationPredicate))
        this.addTransition(StateMachineTransition(steps - 1, 0, NotPredicate(terminationPredicate)))
        return super.build()
    }
}