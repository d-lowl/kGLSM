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
    private val unconditionalPredicate = UnconditionalPredicate()
    private var startingNodeName: String? = null
    private var lastNodeName: String? = null
    /**
     * Add a step to the sequence
     *
     * @param[strategy] Strategy to use as a step
     */
    fun addStep(nodeName: String, strategy: Strategy<T, U>): SequentialStateMachineBuilder<T, U> {
        this.addNode(nodeName, strategy)
        if (startingNodeName == null) {
            startingNodeName = nodeName
        }
        if (lastNodeName != null) this.addTransition(lastNodeName!!, StateMachineTransition(nodeName, unconditionalPredicate))
        lastNodeName = nodeName
        return this
    }

    /**
     * Build Sequential State Machine
     */
    fun build(): StateMachine<T, U> {
        if (lastNodeName == null || startingNodeName == null) throw Exception("No nodes have been added")
        this.addTransition(lastNodeName!!, StateMachineTransition(StateMachine.TERMINATION_STATE_LABEL, terminationPredicate))
        this.addTransition(lastNodeName!!, StateMachineTransition(startingNodeName!!, NotPredicate(terminationPredicate)))
        return super.build(startingNodeName!!)
    }
}