package space.d_lowl.kglsm.simulatedannealing.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.simulatedannealing.memory.attribute.Temperature
import kotlin.math.exp

class AnnealingStep<T> : Strategy<T, BasicSolution<T>>() {
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val candidateNeighbour = searchSpace.getRandomNeighbour(memory.currentSolution.solution)
        val candidateNeighbourCost = costFunction(candidateNeighbour)
        val temperature = memory.getAttribute<Temperature>().temperature
        if (
                memory.currentSolution.cost > candidateNeighbourCost ||
                metropolisCondition(memory.randomVariable, memory.currentSolution.cost, candidateNeighbourCost, temperature)
        ) {
            memory.update(BasicSolution(candidateNeighbour, candidateNeighbourCost))
        }
        memory.updateBest()
    }

    override fun toString(): String = "Simulated Annealing Step"

    companion object {
        fun metropolisCondition(randomVariable: Double, lowerCost: Double, higherCost: Double, temperature: Double) =
                randomVariable < exp((lowerCost - higherCost) / temperature)
    }
}