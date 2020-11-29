package space.d_lowl.kglsm.general.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory

/**
 * No Improvement predicate
 *
 * @param[maxSteps] Maximum steps without improvement
 */
class NoImprovementPredicate(private val maxSteps: Int): TransitionPredicate<Memory<*, *>>
{
    /**
     * The predicate is satisfied if number of steps without improvement reaches or exceeds [maxSteps]
     *
     * @param[memory] Memory
     */
    override fun isSatisfied(memory: Memory<*, *>): Boolean = memory.noImprovementCount >= maxSteps
    override fun toString(): String = "NO_IMPROVEMENT >= $maxSteps"
}