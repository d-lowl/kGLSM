package space.d_lowl.kglsm.problem

import space.d_lowl.kglsm.general.space.isRepeating
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

typealias DistanceFunction = (NDPoint, NDPoint) -> Double

class NDPoint(vararg val coords: Double) {
    fun getDimensions(): Int = coords.size

    override fun toString(): String = "{${coords.joinToString(",")}}"
    companion object {
        fun euclideanDistance(a: NDPoint, b: NDPoint): Double =
                sqrt(a.coords.zip(b.coords).map { (it.first - it.second).pow(2) }.sum())
    }
}

class TSP(
        private val points: List<NDPoint>,
        private val cycle: Boolean = false,
        private val dist: DistanceFunction = (NDPoint)::euclideanDistance
): Problem<Int> {
    val size: Int
        get() = points.size

    init {
        val dimensions = points.first().getDimensions()
        require(points.all { it.getDimensions() == dimensions })
    }

    override fun getSolutionCost(solution: Array<Int>): Double {
        // Solution must be a (partial) permutation
        require(solution.isNotEmpty())
        require(!solution.isRepeating())
        require(solution.min()!! >= 0)
        require(solution.max()!! < points.size)

        var pathCost = solution.asIterable().zipWithNext().map { dist(points[it.first], points[it.second]) }.sum()
        // If permutation is complete and cycle is expected
        if (cycle && solution.size == points.size) {
            pathCost += dist(points[solution.first()], points[solution.last()])
        }
        return pathCost
    }

    override fun toString(): String = "[${points.joinToString(";")}]"

    companion object {
        fun getRandomInstance(
                dimensions: Int,
                noPoints: Int,
                cycle: Boolean = false,
                dist: DistanceFunction = (NDPoint)::euclideanDistance
        ) = TSP(
                List(noPoints) { NDPoint(*(DoubleArray(dimensions) { Random.nextDouble() })) },
                cycle,
                dist
        )

        fun fromFile(filepath: String): TSP {
            var isNodeReadingMode = false
            val iterator = File(filepath).readLines().iterator()
            while (!isNodeReadingMode && iterator.hasNext()) {
                val value = iterator.next().split(":").map { it.trim() }
                when (value.first()) {
                    "EDGE_WEIGHT_TYPE" -> {
                        if (value.last() != "EUC_2D") {
                            throw Exception("Only problem instances with euclidean distance are supported")
                        }
                    }
                    "NODE_COORD_SECTION" -> { isNodeReadingMode = true }
                }
            }

            val points = iterator.asSequence().map { line ->
                val values = Regex("\\d+").findAll(line).map { it.value.toDouble() }.drop(1).toList()
                NDPoint(*values.toDoubleArray())
            }.filter { it.getDimensions() == 2 }.toList()

            return TSP(points, true)
        }
    }
}