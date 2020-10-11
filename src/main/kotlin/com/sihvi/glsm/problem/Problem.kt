package com.sihvi.glsm.problem

interface Problem<T> {
    fun evaluate(solution: Array<T>): Double
}