package space.d_lowl.kglsm.general.strategy

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace

/**
 * Interface of a search strategy
 *
 * @param[T] Solution entity type
 * @param[U] Solution type
 */
abstract class Strategy<T, U> {
    /**
     * Perform a step -- modify [memory] according to the strategy
     *
     * @param[memory] memory
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    abstract fun step(memory: Memory<T, U>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>)
}