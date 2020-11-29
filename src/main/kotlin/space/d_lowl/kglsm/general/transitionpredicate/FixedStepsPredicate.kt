package space.d_lowl.kglsm.general.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory

/**
 * Fixed Steps predicate
 *
 * @param[maxSteps] Maximum number of steps
 */
class FixedStepsPredicate(private val maxSteps: Int): TransitionPredicate<Memory<*, *>>
{
    /**
     * The predicate is satisfied if step count reaches or exceeds [maxSteps]
     *
     * @param[memory] Memory
     */
    override fun isSatisfied(memory: Memory<*, *>): Boolean = memory.stepCount >= maxSteps
    override fun toString(): String = "STEPS >= $maxSteps"
}