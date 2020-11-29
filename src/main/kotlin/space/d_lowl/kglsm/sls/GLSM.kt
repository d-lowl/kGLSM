package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate
import mu.KotlinLogging

/**
 * Generalised Local Search Machine
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 * @param[stateMachine] GLSM state machine
 */
open class GLSM<T, U>(
        private val stateMachine: StateMachine<T, U>
) {
    /**
     * Run GLSM
     *
     * @param[memory] Initial memory
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    fun solve(memory: Memory<T, U>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>): BasicSolution<T> {
        logger.info { "Starting GLSM" }
        logger.info { "Initial strategy state: ${stateMachine.strategy}" }
        logger.info { "Initial memory:\n$memory" }
        while (!stateMachine.isFinished()) {
            stateMachine.strategy?.step(memory, searchSpace, costFunction)
            logger.info { "Step #${memory.stepCount} finished" }
            memory.stepCount++
            stateMachine.transition(memory)
            logger.info { "Next strategy state: ${stateMachine.strategy}" }
        }
        logger.info { "Solving finished" }
        logger.info { "Final memory:\n$memory" }
        return memory.bestSolution
    }

    override fun toString(): String = stateMachine.toString()

    /**
     * Returns ASCII representation of this GLSM
     *
     * Requires graph-easy to be installed, check README.md for details
     */
    fun toASCII(): String? = stateMachine.toASCII()

    companion object {
        private val logger = KotlinLogging.logger {}

        /**
         * Returns single strategy GLSM
         *
         * @param[T] Solution entity type
         * @param[U] Solution type
         * @param[strategy] Strategy to use
         * @param[terminationPredicate] Termination predicate
         */
        fun <T, U> getSingleStrategyGLSM(
            strategy: Strategy<T, U>,
            terminationPredicate: TransitionPredicate<Memory<T, U>>
        ): GLSM<T, U> {
            val stateMachine = StateMachineBuilder<T, U>()
                    .addStrategy(strategy)
                    .addTransition(StateMachineTransition(0, -1, terminationPredicate))
                    .build()
            return GLSM(stateMachine)
        }
    }
}