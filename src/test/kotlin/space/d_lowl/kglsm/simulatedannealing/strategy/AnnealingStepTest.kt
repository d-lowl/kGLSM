package space.d_lowl.kglsm.simulatedannealing.strategy

import org.junit.jupiter.api.Test
import space.d_lowl.kglsm.simulatedannealing.strategy.AnnealingStep.Companion.metropolisCondition

internal class AnnealingStepTest {

    @Test
    fun metropolisConditionTest() {
        // Cost equal, should accept most of the time
        assert(metropolisCondition(0.1, 1.0, 1.0, 20.0))
        assert(metropolisCondition(0.5, 1.0, 1.0, 20.0))
        assert(metropolisCondition(0.9, 1.0, 1.0, 20.0))

        // Cost better, should accept all of the time
        assert(metropolisCondition(0.1, 1.0, 0.5, 20.0))
        assert(metropolisCondition(0.5, 1.0, 0.5, 20.0))
        assert(metropolisCondition(0.9, 1.0, 0.5, 20.0))

        // High cost diff
        assert(metropolisCondition(0.05, 1.0, 50.0, 20.0))
        assert(!metropolisCondition(0.1, 1.0, 50.0, 20.0))
        assert(!metropolisCondition(0.5, 1.0, 50.0, 20.0))
        assert(!metropolisCondition(0.9, 1.0, 50.0, 20.0))

        // Low cost diff, high temperature
        assert(!metropolisCondition(0.99, 1.0, 1.5, 20.0))
        assert(metropolisCondition(0.1, 1.0, 1.5, 20.0))
        assert(metropolisCondition(0.5, 1.0, 1.5, 20.0))
        assert(metropolisCondition(0.9, 1.0, 1.5, 20.0))

        // Low cost diff, low temperature
        assert(metropolisCondition(0.05, 1.0, 1.5, 0.2))
        assert(!metropolisCondition(0.1, 1.0, 1.5, 0.2))
        assert(!metropolisCondition(0.5, 1.0, 1.5, 0.2))
        assert(!metropolisCondition(0.9, 1.0, 1.5, 0.2))
    }
}