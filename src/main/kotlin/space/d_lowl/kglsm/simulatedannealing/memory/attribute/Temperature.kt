package space.d_lowl.kglsm.simulatedannealing.memory.attribute

import space.d_lowl.kglsm.general.memory.attribute.MemoryAttribute

class Temperature(var temperature: Double = 0.0): MemoryAttribute {
    override fun toString(): String = "Temperature ($temperature)"
}