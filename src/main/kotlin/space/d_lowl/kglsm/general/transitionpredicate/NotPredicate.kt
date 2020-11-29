package space.d_lowl.kglsm.general.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory

/**
 * Not predicate
 *
 * @param[M] Memory type
 * @param[predicate] predicate to negate
 */
class NotPredicate<M : Memory<*, *>>(private val predicate: TransitionPredicate<M>) : TransitionPredicate<M> {

    /**
     * The predicate is satisfied when the enclosed [predicate] is not satisfied
     *
     * @param[memory] Memory
     */
    override fun isSatisfied(memory: M): Boolean = !predicate.isSatisfied(memory)
    override fun toString(): String = "¬($predicate)"
}