package space.d_lowl.kglsm.general.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace

/**
 * Random Walk Strategy
 *
 * @param[T] Solution entity type
 * @param[updateBest] flag, set true to update best solution after the step
 */
class RandomWalkStrategy<T>(private val updateBest: Boolean = false) : Strategy<T, BasicSolution<T>>() {
    /**
     * At every step, the next solution is chosen randomly from a neighbourhood
     *
     * @param[memory] memory
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val newSolution = searchSpace.getRandomNeighbour(memory.currentSolution.solution)
        val newCost = costFunction(newSolution)
        memory.update(BasicSolution(newSolution, newCost))
        if (updateBest) memory.updateBest()
    }

    override fun toString(): String = "Random Walk${if (updateBest) " [updating best]" else ""}"
}