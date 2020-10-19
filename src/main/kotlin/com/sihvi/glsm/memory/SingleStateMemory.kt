package com.sihvi.glsm.memory

/**
 * Data class holds a solution array and a corresponding cost
 *
 * @param[T] Solution entity type
 * @param[solution] Solution array
 * @param[cost] Solution cost
 */
data class SingleStateSolution<T>(val solution: Array<T>, val cost: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleStateSolution<*>

        if (!solution.contentEquals(other.solution)) return false
        if (cost != other.cost) return false

        return true
    }

    override fun hashCode(): Int {
        var result = solution.contentHashCode()
        result = 31 * result + cost.hashCode()
        return result
    }
}

/**
 * Single state memory, that holds a current and a best (so far) solution.
 *
 * @param[T] Solution entity type
 * @param[initialSolution] Solution to initialise memory with
 */
class SingleStateMemory<T>(
        initialSolution: SingleStateSolution<T>
) :
        IMemory<SingleStateSolution<T>>,
        HasStepCount,
        HasNoImprovementCount
{

    private var _currentSolution: SingleStateSolution<T> = initialSolution
    override fun getCurrentSolution(): SingleStateSolution<T>  = _currentSolution

    private var _bestSolution: SingleStateSolution<T> = initialSolution
    override fun getBestSolution(): SingleStateSolution<T> = _bestSolution

    private var _stepCount: Int = 0
    override fun getStepCount(): Int = _stepCount

    private var _noImprovementCount: Int = 0
    override fun getNoImprovementCount(): Int = _noImprovementCount

    override fun update(solution: SingleStateSolution<T>) {
        _currentSolution = solution
        updateBestSolution(solution)
        _stepCount++
    }

    private fun updateBestSolution(solution: SingleStateSolution<T>) {
        if (solution.cost < _bestSolution.cost) {
            _bestSolution = solution
            _noImprovementCount = 0
        } else {
            _noImprovementCount++
        }
    }
}