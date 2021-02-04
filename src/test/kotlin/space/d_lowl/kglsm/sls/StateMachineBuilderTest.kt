package space.d_lowl.kglsm.sls

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import space.d_lowl.kglsm.general.transitionpredicate.UnconditionalPredicate
import space.d_lowl.kglsm.mock.DummyStrategy

internal class StateMachineBuilderTest {

    private val dummyStrategy = DummyStrategy()
    private val predicate = UnconditionalPredicate()

    @Test
    fun testBuildSimple() {
        stateMachine<Unit, Unit> {
            entrypoint = "DUMMY"
            node {
                name = "DUMMY"
                strategy = dummyStrategy
                transition {
                    to = StateMachine.TERMINATION_STATE_LABEL
                    transitionPredicate = predicate
                }
            }
        }
    }

    @Test
    fun testBuildNonTerminating() {
        var exception = assertThrows<Exception> {
            stateMachine<Unit, Unit> {
                entrypoint = "DUMMY"
                node {
                    name = "DUMMY"
                    strategy = dummyStrategy
                }
            }
        }
        assert(exception.message == "No path leads to termination")

        exception = assertThrows<Exception> {
            stateMachine<Unit, Unit> {
                entrypoint = "DUMMY"
                node {
                    name = "DUMMY"
                    strategy = dummyStrategy
                    transition {
                        to = "DUMMY"
                        transitionPredicate = predicate
                    }
                }
            }
        }
        assert(exception.message == "No path leads to termination")
    }
}