package com.sihvi.glsm.memory

class Memory<T>(initialSolution: Array<T>, initialCost: Double) {
    var currentSolution: Array<T> = initialSolution
        private set
    var currentSolutionCost: Double = initialCost
        private set
    var bestSolution: Array<T> = initialSolution
        private set
    var bestSolutionCost: Double = initialCost
        private set
    var stepCount: Int = 0
        private set
    var noImprovementCount: Int = 0
        private set

    fun update(solution: Array<T>, cost: Double) {
        currentSolution = solution
        currentSolutionCost = cost
        updateBestSolution(solution, cost)
        stepCount++
    }

    private fun updateBestSolution(solution: Array<T>, cost: Double) {
        if (cost < bestSolutionCost) {
            bestSolutionCost = cost
            bestSolution = solution
            noImprovementCount = 0
        } else {
            noImprovementCount++
        }
    }
}