package space.d_lowl.kglsm.problem

typealias CostFunction<T> = (Array<T>) -> Double

/**
 * Interface defining the minimal set of methods for a problem class
 *
 * @param[T] Solution entity type
 */
interface Problem<T> {
    /**
     * Problem instance evaluation function
     *
     * @return cost of the [solution]
     */
    fun getSolutionCost(solution: Array<T>): Double
}