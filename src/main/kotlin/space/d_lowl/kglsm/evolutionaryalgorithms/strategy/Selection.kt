package space.d_lowl.kglsm.evolutionaryalgorithms.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.general.memory.attribute.Stash
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy

typealias SelectionOperator<T> = (Collection<BasicSolution<T>>, Int) -> List<BasicSolution<T>>

/**
 * Selection strategy
 *
 * @param[T] Solution entity type
 * @param[name] Name of selection operator
 * @param[selectionOperator] Selection operator
 */
class Selection<T> (
        private val name: String = "UNK",
        private val selectionOperator: SelectionOperator<T>
): Strategy<T, PopulationSolution<T>>() {
    /**
     * Build a new set of solutions by selecting solutions from stash with selection operator,
     * and clear the stash afterwards.
     *
     * @param[memory] memory, must have [space.d_lowl.kglsm.general.memory.attribute.Stash] attribute
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val stash = memory.getAttribute<Stash<BasicSolution<T>>>()
        memory.currentSolution = selectionOperator(stash.stash, memory.currentSolution.size)
        memory.updateBest()
        stash.clearStash()
    }

    override fun toString(): String = "Selection: $name"

    companion object {
        /**
         * Elitism selection operator
         *
         * Takes [size] best performing solutions from [pool]
         *
         * @param[T] Solution entity type
         * @param[pool] Pool of solutions
         * @param[size] Size of population
         */
        fun <T> elitism(pool: Collection<BasicSolution<T>>, size: Int): List<BasicSolution<T>> =
                pool.sortedBy { it.cost }.take(size)

        /**
         * Elitism Selection strategy
         *
         * @param[T] Solution entity type
         */
        fun <T> elitistSelection(): Selection<T> = Selection("Elitist", Companion::elitism)
    }
}