package space.d_lowl.kglsm.general.strategy

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.sls.GLSM
import space.d_lowl.kglsm.general.space.SearchSpace

class GLSMWrapperStrategy<T, U>(private val glsm: GLSM<T, U>) : Strategy<T, U>() {
    override fun step(memory: Memory<T, U>, searchSpace: SearchSpace<T>, costFunction: CostFunction<T>) {
        glsm.solve(memory, searchSpace, costFunction)
    }
}