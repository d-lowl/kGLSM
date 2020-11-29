package space.d_lowl.kglsm.problem

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SATClauseTest {

    @Test
    fun evaluateClauseOfOne() {
        val literal = SATLiteral(0, false)
        val clause = SATClause(arrayOf(literal))

        assertTrue(clause.evaluate(arrayOf(true)))
        assertFalse(clause.evaluate(arrayOf(false)))
    }

    @Test
    fun evaluateClauseOfOneNegated() {
        val literal = SATLiteral(0, true)
        val clause = SATClause(arrayOf(literal))

        assertFalse(clause.evaluate(arrayOf(true)))
        assertTrue(clause.evaluate(arrayOf(false)))
    }

    @Test
    fun evaluateClauseOfMultiple() {
        val clause = SATClause(arrayOf(
            SATLiteral(1, false),
            SATLiteral(3, true)
        ))

        assertFalse(clause.evaluate(arrayOf(
            true, false, true, true, false
        )))
        assertTrue(clause.evaluate(arrayOf(
            true, true, true, false, false
        )))
    }
}