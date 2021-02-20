package space.d_lowl.kglsm.simulatedannealing.strategy

import org.junit.jupiter.api.Test
import space.d_lowl.kglsm.simulatedannealing.strategy.AnnealingStep.Companion.metropolisCriterion

internal class AnnealingStepTest {

    @Test
    fun metropolisConditionTest() {
        // Cost equal, should accept most of the time
        assert(metropolisCriterion(0.1, 1.0, 1.0, 20.0))
        assert(metropolisCriterion(0.5, 1.0, 1.0, 20.0))
        assert(metropolisCriterion(0.9, 1.0, 1.0, 20.0))

        // Cost better, should accept all of the time
        assert(metropolisCriterion(0.1, 1.0, 0.5, 20.0))
        assert(metropolisCriterion(0.5, 1.0, 0.5, 20.0))
        assert(metropolisCriterion(0.9, 1.0, 0.5, 20.0))

        // High cost diff
        assert(metropolisCriterion(0.05, 1.0, 50.0, 20.0))
        assert(!metropolisCriterion(0.1, 1.0, 50.0, 20.0))
        assert(!metropolisCriterion(0.5, 1.0, 50.0, 20.0))
        assert(!metropolisCriterion(0.9, 1.0, 50.0, 20.0))

        // Low cost diff, high temperature
        assert(!metropolisCriterion(0.99, 1.0, 1.5, 20.0))
        assert(metropolisCriterion(0.1, 1.0, 1.5, 20.0))
        assert(metropolisCriterion(0.5, 1.0, 1.5, 20.0))
        assert(metropolisCriterion(0.9, 1.0, 1.5, 20.0))

        // Low cost diff, low temperature
        assert(metropolisCriterion(0.05, 1.0, 1.5, 0.2))
        assert(!metropolisCriterion(0.1, 1.0, 1.5, 0.2))
        assert(!metropolisCriterion(0.5, 1.0, 1.5, 0.2))
        assert(!metropolisCriterion(0.9, 1.0, 1.5, 0.2))
    }
}