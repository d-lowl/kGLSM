package space.d_lowl.kglsm.general.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory

/**
 * Unconditional predicate
 */
class UnconditionalPredicate: TransitionPredicate<Memory<*, *>> {
    /**
     * The predicate is always satisfied
     */
    override fun isSatisfied(memory: Memory<*, *>): Boolean = true
    override fun toString(): String = "⊤"
}