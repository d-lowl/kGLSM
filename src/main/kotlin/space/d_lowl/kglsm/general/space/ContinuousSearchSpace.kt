package space.d_lowl.kglsm.general.space

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

typealias ProbabilityDistribution = (Double, Pair<Double, Double>) -> Double

class ContinuousSearchSpace(private val constraints: List<Pair<Double, Double>>, private val distribution: ProbabilityDistribution) : SearchSpace<Double> {
    init {
        constraints.forEach {
            assert(it.second > it.first) { "Constraint pairs must be in increasing order, got $it" }
        }
    }

    override fun getInitial(): Array<Double> =
            constraints.map { Random.nextDouble(it.first, it.second) }.toTypedArray()

    override fun getNeighbourhood(solution: Array<Double>): Sequence<Array<Double>> {
        TODO("Not yet implemented")
    }

    override fun getRandomNeighbour(solution: Array<Double>): Array<Double> {
        return solution.zip(constraints).map { distribution(it.first, it.second) }.toTypedArray()
    }

    companion object {
        fun getUniformDistribution(): ProbabilityDistribution {
            return fun(_: Double, constraint: Pair<Double, Double>): Double {
                return Random.nextDouble(constraint.first, constraint.second)
            }
        }

        fun getStepUniformDistribution(step: Double): ProbabilityDistribution {
            return fun(current: Double, constraint: Pair<Double, Double>): Double {
                val lowerBound = max(constraint.first, current - step / 2.0)
                val upperBound = min(constraint.second, current + step / 2.0)
                return Random.nextDouble(lowerBound, upperBound)
            }
        }

    }
}