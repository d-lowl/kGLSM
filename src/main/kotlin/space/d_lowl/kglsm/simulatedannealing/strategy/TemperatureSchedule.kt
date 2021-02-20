package space.d_lowl.kglsm.simulatedannealing.strategy

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.simulatedannealing.memory.attribute.Temperature

abstract class TemperatureSchedule<T> : Strategy<T, BasicSolution<T>>()

class LinearTemperatureSchedule<T>(private val coolingStep: Double) : TemperatureSchedule<T>() {
    init {
        require(coolingStep > 0)
    }

    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.getAttribute<Temperature>().temperature -= coolingStep
    }

    override fun toString(): String = "Simulated Annealing Linear Schedule (step = $coolingStep)"
}

class MultiplicativeTemperatureSchedule<T>(private val multiplier: Double) : TemperatureSchedule<T>() {
    init {
        require(multiplier > 0 && multiplier < 1.0)
    }

    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        memory.getAttribute<Temperature>().temperature *= multiplier
    }

    override fun toString(): String = "Simulated Annealing Multiplicative Schedule (multiplier = $multiplier)"
}

class ReciprocalTemperatureSchedule<T>(private val beta: Double) : TemperatureSchedule<T>() {
    init {
        require(beta > 0.0)
    }

    override fun step(memory: Memory<T, BasicSolution<T>>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        val temperature = memory.getAttribute<Temperature>().temperature
        memory.getAttribute<Temperature>().temperature = temperature / (1 + beta * temperature)
    }

    override fun toString(): String = "Simulated Annealing Multiplicative Schedule (beta = $beta)"
}