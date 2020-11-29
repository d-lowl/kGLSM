package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.strategy.Strategy

open class GLSMBuilder<T, U> {
    private val stateMachineBuilder = StateMachineBuilder<T, U>()
    fun addStrategy(strategy: Strategy<T, U>): GLSMBuilder<T, U> {
        stateMachineBuilder.addStrategy(strategy)
        return this
    }

    fun addTransition(transition: StateMachineTransition<Memory<T, U>>): GLSMBuilder<T, U> {
        stateMachineBuilder.addTransition(transition)
        return this
    }

    open fun build(): GLSM<T, U> {
        val stateMachine = stateMachineBuilder.build()
        return GLSM(stateMachine)
    }
}