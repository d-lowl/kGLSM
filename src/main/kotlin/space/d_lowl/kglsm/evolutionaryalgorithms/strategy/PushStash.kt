package space.d_lowl.kglsm.evolutionaryalgorithms.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.general.memory.attribute.Stash
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy

/**
 * Push Stash strategy
 *
 * @param[T] Solution entity type
 */
class PushStash<T>: Strategy<T, PopulationSolution<T>>() {
    /**
     * Pushes current population of solutions to stash
     *
     * @param[memory] memory, must have [space.d_lowl.kglsm.general.memory.attribute.Stash] attribute
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.getAttribute<Stash<BasicSolution<T>>>().addAllToStash(memory.currentSolution)
    }

    override fun toString(): String = "PushStash"
}