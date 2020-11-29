package space.d_lowl.kglsm.general.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory

/**
 * Transition Predicate interface
 *
 * @param[M] Memory type a predicate is applicable to
 */
interface TransitionPredicate<in M : Memory<*, *>> {
    fun isSatisfied(memory: M): Boolean
}