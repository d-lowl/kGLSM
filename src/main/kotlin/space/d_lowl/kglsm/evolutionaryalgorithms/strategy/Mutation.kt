package space.d_lowl.kglsm.evolutionaryalgorithms.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy
import kotlin.random.Random

typealias MutationOperator<T> = (Array<T>) -> Array<T>

/**
 * Mutation strategy
 *
 * @param[T] Solution entity type
 * @param[name] Mutation operator name
 * @param[mutationOperator] Mutation operator
 */
class Mutation<T>(private val name: String = "UNK", private val mutationOperator: MutationOperator<T>): Strategy<T, PopulationSolution<T>>() {
    /**
     * Performs mutation on a population of solutions
     *
     * @param[memory] memory
     * @param[searchSpace] search space for a problem instance
     * @param[costFunction] cost function of a problem instance
     */
    override fun step(memory: Memory<T, PopulationSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.currentSolution = memory.currentSolution.map { mutationOperator(it.solution) }.map { BasicSolution(it, costFunction(it)) }
    }

    override fun toString(): String = "Mutation: $name"

    companion object {
        /**
         * Bit Flip Mutation strategy
         *
         * @param[probability] probability of a single boolean value (or bit) to mutate
         */
        fun bitFlipMutation(probability: Double): Mutation<Boolean> =
                Mutation("Bit-Flip") { arr -> arr.map { if (Random.nextDouble() < probability) !it else it }.toTypedArray() }
    }
}