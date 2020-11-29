package space.d_lowl.kglsm.evolutionaryalgorithms.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.general.memory.pickRandom
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy
import kotlin.random.Random

typealias RecombinationOperator<T> = (Array<T>, Array<T>) -> Array<T>

/**
 * Recombination strategy
 *
 * @param[T] Solution entity type
 * @param[name] Recombination operator name
 * @param[recombinationOperator] Recombination operator
 */
class Recombination<T> (
        private val name: String = "UNK",
        private val recombinationOperator: RecombinationOperator<T>
) : Strategy<T, PopulationSolution<T>>() {
    /**
     * Build a new set of solutions from recombination of random pairs of solutions
     *
     * @param[memory] memory
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.currentSolution = List(memory.currentSolution.size) {
            recombinationOperator(memory.currentSolution.pickRandom(), memory.currentSolution.pickRandom())
        }.map { BasicSolution(it, costFunction(it)) }
    }

    override fun toString(): String = "Recombination: $name"

    companion object {
        /**
         * Single Point Crossover operator
         *
         * @param[T] Solution entity type
         * @param[a] First solution
         * @param[b] Second solution
         */
        fun <T> singlePointCrossover(a: Array<T>, b: Array<T>): Array<T> {
            require(a.size == b.size)
            val crossoverPoint = Random.nextInt(a.size)
            return a.sliceArray(0 until crossoverPoint) + a.sliceArray(crossoverPoint until a.size)
        }

        /**
         * Single Point Crossover Recombination strategy
         *
         * @param[T] Solution entity type
         */
        fun <T> singlePointCrossoverRecombination(): Recombination<T> = Recombination("Single Point Crossover", Companion::singlePointCrossover)
    }
}