package com.sihvi.glsm.sls

import com.sihvi.glsm.mock.DummyStrategy
import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.transitionpredicate.UnconditionalPredicate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class StateMachineBuilderTest {

    private val dummyStrategy = DummyStrategy()
    private val predicate = UnconditionalPredicate()
    private lateinit var builder: StateMachineBuilder<Unit, Unit, Memory<Unit, Unit>, SearchSpace<Unit>>

    @BeforeEach
    fun setUp() {
        builder = StateMachineBuilder()
    }

    @Test
    fun testBuildSimple() {
        builder
            .addStrategy(dummyStrategy)
            .addTransition(StateMachineTransition(0, -1, predicate))
            .build()
    }

    @Test
    fun testBuildNonTerminating() {
        val exception = assertThrows<Exception> {
            builder
                .addStrategy(dummyStrategy)
                .build()
        }
        assert(exception.message == "No path leads to termination")
    }

    @Test
    fun testBuildTransitionFromTermination() {
        val exception = assertThrows<Exception> {
            builder
                .addTransition(StateMachineTransition(-1, 0, predicate))
                .build()
        }
        assert(exception.message == "Termination must not have outgoing transitions")
    }
}