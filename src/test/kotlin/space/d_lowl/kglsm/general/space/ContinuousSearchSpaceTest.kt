package space.d_lowl.kglsm.general.space

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import space.d_lowl.kglsm.misc.TruncatedNormal
import java.lang.AssertionError

internal class ContinuousSearchSpaceTest {
    private val constraint = Pair(-10.0, 10.0)
    private val erroneousConstraint = Pair(10.0, -10.0)
    private val noDistributionTests = 100000

    private fun withinConstraints(x: Double, constraint: Pair<Double, Double>) =
            constraint.first <= x && x <= constraint.second

    @Test
    fun init() {
        assertDoesNotThrow { ContinuousSearchSpace(listOf(constraint), ContinuousSearchSpace.getUniformDistribution()) }
        assertThrows<AssertionError> { ContinuousSearchSpace(listOf(erroneousConstraint), ContinuousSearchSpace.getUniformDistribution()) }
    }

    @Test
    fun getInitial() {
        val initial = ContinuousSearchSpace(List(10) { constraint }, ContinuousSearchSpace.getUniformDistribution()).getInitial()
        initial.forEach {
            assert(it >= constraint.first && it <= constraint.second) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }
    }

    @Test
    fun unformDistribution() {
        val distribution = ContinuousSearchSpace.getUniformDistribution()
        (0..noDistributionTests).map { distribution(0.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }
    }

    @Test
    fun stepUniformDistribution() {
        val largeStepDistribution = ContinuousSearchSpace.getStepUniformDistribution(5.0)

        // Lower
        (0..noDistributionTests).map { largeStepDistribution(-10.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        // Middle
        (0..noDistributionTests).map { largeStepDistribution(0.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        // Upper
        (0..noDistributionTests).map { largeStepDistribution(10.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        val smallStepDistribution = ContinuousSearchSpace.getStepUniformDistribution(0.25)

        // Lower
        (0..noDistributionTests).map { smallStepDistribution(-10.0, constraint) }.forEach {
            assert(withinConstraints(it, Pair(-10.0, -5.0))) { "Got $it, expected in [${-10.0}, ${-5.0}]" }
        }

        // Middle
        (0..noDistributionTests).map { smallStepDistribution(0.0, constraint) }.forEach {
            assert(withinConstraints(it, Pair(-5.0, 5.0))) { "Got $it, expected in [${-5.0}, ${5.0}]" }
        }

        // Upper
        (0..noDistributionTests).map { smallStepDistribution(10.0, constraint) }.forEach {
            assert(withinConstraints(it, Pair(5.0, 10.0))) { "Got $it, expected in [${5.0}, ${10.0}]" }
        }
    }

    @Test
    fun truncatedNormalDistribution() {
        val truncatedNormalDistribution = TruncatedNormal.getTruncatedNormalDistribution(1.0)

        // Lower
        (0..noDistributionTests).map { truncatedNormalDistribution(-10.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        // Middle
        (0..noDistributionTests).map { truncatedNormalDistribution(0.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        // Upper
        (0..noDistributionTests).map { truncatedNormalDistribution(10.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        val wideTruncatedNormalDistribution = TruncatedNormal.getTruncatedNormalDistribution(10.0)

        // Lower
        (0..noDistributionTests).map { wideTruncatedNormalDistribution(-10.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        // Middle
        (0..noDistributionTests).map { wideTruncatedNormalDistribution(0.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }

        // Upper
        (0..noDistributionTests).map { wideTruncatedNormalDistribution(10.0, constraint) }.forEach {
            assert(withinConstraints(it, constraint)) { "Got $it, expected in [${constraint.first}, ${constraint.second}]" }
        }
    }
}