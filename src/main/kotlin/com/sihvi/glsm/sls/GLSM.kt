package com.sihvi.glsm.sls

import com.sihvi.glsm.memory.BasicSolution
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.problem.Problem
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy
import com.sihvi.glsm.transitionpredicate.TransitionPredicate
import mu.KotlinLogging

/**
 * Generalised Local Search Machine
 *
 * @param[T] Solution entity type
 * @param[S] Solution type
 * @param[M] Memory type
 * @param[N] Search space type
 * @param[stateMachine] GLSM strategy state machine
 */
open class GLSM<T, U>(
        private val stateMachine: StateMachine<T, U>
) {
    fun solve(memory: Memory<T, U>, space: SearchSpace<T>, costFunction: CostFunction<T>): BasicSolution<T> {
        logger.info { "Starting GLSM" }
        logger.info { "Initial strategy state: ${stateMachine.strategy}" }
        logger.info { "Initial memory:\n$memory" }
        while (!stateMachine.isFinished()) {
            stateMachine.strategy?.step(memory, space, costFunction)
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

    fun toASCII(): String? = stateMachine.toASCII()

    companion object {
        private val logger = KotlinLogging.logger {}

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