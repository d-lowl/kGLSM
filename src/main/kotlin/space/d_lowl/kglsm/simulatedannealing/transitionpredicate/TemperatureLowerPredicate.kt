package space.d_lowl.kglsm.simulatedannealing.transitionpredicate

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.simulatedannealing.memory.attribute.Temperature
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate

class TemperatureLowerPredicate(private val temperatureThreshold: Double): TransitionPredicate<Memory<*, *>> {
    override fun isSatisfied(memory: Memory<*, *>): Boolean =
            memory.getAttribute<Temperature>().temperature <= temperatureThreshold

    override fun toString(): String = "Temperature <= $temperatureThreshold"
}