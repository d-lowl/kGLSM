package space.d_lowl.kglsm.iterativeimprovement.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy

/**
 * Enum representing mode of Iterative Improvement algorithm
 */
enum class IIMode {
    BEST, FIRST
}

/**
 * Iterative Improvement strategy
 *
 * @param[T] Solution entity type
 * @param[mode] Iterative Improvement strategy mode
 * @param[updateBest] flag, set true to update best solution after the step
 */
class IterativeImprovementStrategy<T>(private val mode: IIMode, private val updateBest: Boolean = true)
    : Strategy<T, BasicSolution<T>>() {
    /**
     * If there's a neighbouring solution that improves the current cost, select the next solution according to the [mode]:
     * - [IIMode.BEST] -- the best improvement
     * - [IIMode.FIRST] -- the first improvement
     *
     * @param[memory] memory
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val neighbourhood = searchSpace.getNeighbourhood(memory.currentSolution.solution)
        val bestCost = memory.currentSolution.cost
        var newSolution = memory.currentSolution

        when (mode) {
            IIMode.FIRST -> {
                for (neighbour in neighbourhood) {
                    val cost = costFunction(neighbour)
                    if (cost < bestCost) {
                        newSolution = BasicSolution(neighbour, cost)
                        break
                    }
                }
            }
            IIMode.BEST -> {
                val neighbourhoodSolutions = neighbourhood
                        .map { costFunction(it) }
                        .zip(neighbourhood)
                        .map { BasicSolution(it.second, it.first) }
                val improvement = neighbourhoodSolutions.filter { it.cost < bestCost }.toList()
                newSolution = if (improvement.isEmpty()) {
                    memory.currentSolution
                } else {
                    improvement.minBy { it.cost }!!
                }
            }
        }

        memory.update(newSolution)
        if (updateBest) memory.updateBest()
    }

    override fun toString(): String {
        val subtype = when (mode) {
            IIMode.BEST -> "Best"
            IIMode.FIRST -> "First"
        }
        return "$subtype Iterative Improvement${if (updateBest) " [updating best]" else ""}"
    }
}