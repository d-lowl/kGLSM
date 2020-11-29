package space.d_lowl.kglsm.general.memory

import space.d_lowl.kglsm.general.memory.attribute.MemoryAttribute
import space.d_lowl.kglsm.problem.Problem
import space.d_lowl.kglsm.general.space.SearchSpace
import kotlin.random.Random

typealias PopulationSolution<T> = List<BasicSolution<T>>
fun <T> PopulationSolution<T>.pickRandom(): Array<T> = this[Random.nextInt(this.size)].solution

class PopulationCurrentState<T>(override var currentSolution: PopulationSolution<T>) : CurrentState<T, PopulationSolution<T>> {
    override fun getCurrentBest(): BasicSolution<T> = currentSolution.minBy { it.cost }!!

    override fun toString(): String = "[${currentSolution.joinToString("\n")}]"

    companion object {
        fun <T> getInitialSolution(searchSpace: SearchSpace<T>, problem: Problem<T>, populationSize: Int): PopulationSolution<T> {
            require(populationSize > 0)
            return List(populationSize) {searchSpace.getInitial()}.map { BasicSolution(it, problem.getSolutionCost(it)) }
        }
    }
}



class PopulationMemory<T>(initialSolution: PopulationSolution<T>, memoryAttributes: List<MemoryAttribute> = listOf()) : Memory<T, PopulationSolution<T>>(
        PopulationCurrentState(initialSolution),
        PopulationCurrentState(initialSolution).getCurrentBest(),
        memoryAttributes
)