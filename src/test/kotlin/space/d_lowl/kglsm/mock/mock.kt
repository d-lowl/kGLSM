package space.d_lowl.kglsm.mock

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy

class DummyStrategy: Strategy<Unit, Unit>() {
    override fun step(memory: Memory<Unit, Unit>, searchSpace: SearchSpace<Unit>, costFunction: CostFunction<Unit>) {
        TODO("Not yet implemented")
    }
}