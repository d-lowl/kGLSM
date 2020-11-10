package com.sihvi.glsm.mock

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.problem.CostFunction
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.strategy.Strategy

class DummyStrategy: Strategy<Unit, Memory<Unit, Unit>, SearchSpace<Unit>>() {
    override fun step(memory: Memory<Unit, Unit>, searchSpace: SearchSpace<Unit>, costFunction: CostFunction<Unit>) {
        TODO("Not yet implemented")
    }
}