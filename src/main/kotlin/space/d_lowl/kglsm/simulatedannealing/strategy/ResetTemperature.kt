package space.d_lowl.kglsm.simulatedannealing.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.simulatedannealing.memory.attribute.Temperature
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy

class ResetTemperature<T>(private val initialTemperature: Double) : Strategy<T, BasicSolution<T>>() {
    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.getAttribute<Temperature>().temperature = initialTemperature
    }

    override fun toString(): String = "Reset Temperature to $initialTemperature"

}