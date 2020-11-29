package space.d_lowl.kglsm.general.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory

/**
 * Probabilistic Predicate
 */
class ProbabilisticPredicate(
        private val from : Double = 0.0,
        private val to : Double = 1.0
) : TransitionPredicate<Memory<*, *>> {
    /**
     * The predicate is satisfied if random variable is between [from] and [to].
     *
     * @param[memory] Memory
     */
    override fun isSatisfied(memory: Memory<*, *>): Boolean = memory.randomVariable in from..to
    override fun toString(): String {
        return if (from == 0.0 && to == 1.0) "⊤"
        else if (from == 0.0) "X <= $to"
        else if (to == 1.0) "X >= $from"
        else "$from <= X <= $to"
    }
}