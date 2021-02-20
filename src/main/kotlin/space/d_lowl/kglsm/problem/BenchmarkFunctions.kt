package space.d_lowl.kglsm.problem

import kotlin.math.*

fun getAckley(noDimensions: Int, a: Double = 20.0, b: Double = 0.2, c: Double = PI * 2): CostFunction<Double> {
    return fun (x: Array<Double>): Double {
        assert(noDimensions == x.size) { "Input must be of size $noDimensions" };
        val firstSum = x.map { it*it }.sum()
        val secondSum = x.map { cos(c * it) }.sum()
        return -a * exp(-b * sqrt(firstSum / noDimensions)) - exp(secondSum / noDimensions) + a + E
    }
}