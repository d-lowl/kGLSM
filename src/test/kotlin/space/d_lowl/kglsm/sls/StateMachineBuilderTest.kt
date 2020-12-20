package space.d_lowl.kglsm.sls

import space.d_lowl.kglsm.mock.DummyStrategy
import space.d_lowl.kglsm.general.transitionpredicate.UnconditionalPredicate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class StateMachineBuilderTest {

    private val dummyStrategy = DummyStrategy()
    private val predicate = UnconditionalPredicate()
    private lateinit var builder: StateMachineBuilder<Unit, Unit>

    @BeforeEach
    fun setUp() {
        builder = StateMachineBuilder()
    }

    @Test
    fun testBuildSimple() {
        builder
                .addNode("DUMMY", dummyStrategy)
                .addTransition("DUMMY", StateMachineTransition(StateMachine.TERMINATION_STATE_LABEL, predicate))
                .build("DUMMY")
    }

    @Test
    fun testBuildNonTerminating() {
        val exception = assertThrows<Exception> {
            builder
                    .addNode("DUMMY", dummyStrategy)
                    .build("DUMMY")
        }
        assert(exception.message == "No path leads to termination")
    }

    @Test
    fun testBuildTransitionFromTermination() {
        val exception = assertThrows<Exception> {
            builder
                    .addNode("DUMMY", dummyStrategy)
                    .addTransition(StateMachine.TERMINATION_STATE_LABEL, StateMachineTransition("DUMMY", predicate))
                    .build("DUMMY")
        }
        assert(exception.message == "Termination must not have outgoing transitions")
    }
}