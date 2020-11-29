package space.d_lowl.kglsm.problem

import kotlin.random.Random

data class SATLiteral(val index: Int, val negated: Boolean) {
    override fun toString(): String = "${if (negated) "¬" else ""}x$index"
}

class SATClause(private val literals: Array<SATLiteral>) {
    val maxIndex = literals.map { it.index }.max() ?: 0

    fun evaluate(assignment: Array<Boolean>): Boolean {
        assert(assignment.size - 1 >= maxIndex)
        return literals.any {
            val b = assignment[it.index]
            if (!it.negated) b else !b
        }
    }

    override fun toString(): String = "(${literals.joinToString("∨")})"
}

class MAXSAT(private val maxIndex: Int, private val clauses: List<SATClause>): Problem<Boolean> {
    override fun getSolutionCost(solution: Array<Boolean>): Double {
        assert(solution.size - 1 == maxIndex)
        return 1.0 - clauses.count { it.evaluate(solution) }.toDouble() / clauses.size
    }

    override fun toString(): String = clauses.joinToString("∧")

    companion object {
        fun getRandomInstance(noVariables: Int, noClauses: Int, maxPerClause: Int): MAXSAT =
            MAXSAT(noVariables, List(noClauses) {
                SATClause(Array(maxPerClause) {
                    SATLiteral(Random.nextInt(noVariables), Random.nextBoolean())
                })
            })
    }
}

