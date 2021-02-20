package space.d_lowl.kglsm.mock

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.space.SearchSpace
import space.d_lowl.kglsm.general.strategy.Strategy
import space.d_lowl.kglsm.problem.CostFunction

class DummyStrategy : Strategy<Unit, Unit>() {
    override fun step(memory: Memory<Unit, Unit>, searchSpace: SearchSpace<Unit>, costFunction: CostFunction<Unit>) {
        TODO("Not yet implemented")
    }
}

class DummySearchSpace : SearchSpace<Unit> {
    override fun getInitial(): Array<Unit> {
        TODO("Not yet implemented")
    }

    override fun getNeighbourhood(solution: Array<Unit>): Sequence<Array<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getRandomNeighbour(solution: Array<Unit>): Array<Unit> {
        TODO("Not yet implemented")
    }

}