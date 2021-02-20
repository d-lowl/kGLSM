package space.d_lowl.kglsm.misc

import space.d_lowl.kglsm.general.space.ProbabilityDistribution
import space.d_lowl.kglsm.misc.NormalDist.cdf01
import space.d_lowl.kglsm.misc.NormalDist.inverseF01
import kotlin.random.Random

/**
 * Truncated normal distribution
 *
 * [Reference Java implementation](https://github.com/mizzao/libmao/blob/master/src/main/java/net/andrewmao/probability/TruncatedNormal.java)
 */
class TruncatedNormal(private val mu: Double, private val sigma: Double, lb: Double, ub: Double) {
    private val cdfA: Double
    private val cdfB: Double
    private val Z: Double

    init {
        require(!(mu.isNaN() || sigma.isNaN() || lb.isNaN() || ub.isNaN())) { "Cannot take NaN as an argument" }
        cdfA = cdf01((lb - mu) / sigma)
        cdfB = cdf01((ub - mu) / sigma)
        Z = cdfB - cdfA
    }

    fun sample(): Double {
        val r: Double = Random.nextDouble() * Z + cdfA
        return mu + sigma * inverseF01(r)
    }

    companion object {
        fun getTruncatedNormalDistribution(sigma: Double = 1.0): ProbabilityDistribution {
            return fun (current: Double, constraint: Pair<Double, Double>): Double =
                    TruncatedNormal(current, sigma, constraint.first, constraint.second).sample()
        }
    }
}