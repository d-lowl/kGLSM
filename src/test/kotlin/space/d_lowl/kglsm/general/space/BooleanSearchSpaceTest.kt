package space.d_lowl.kglsm.general.space

import org.junit.jupiter.api.Test

internal class BooleanSearchSpaceTest {

    private val space = BooleanSearchSpace(3)
    private val currentSolution = arrayOf(false, true, false)
    private val expectedNeighbours = arrayListOf<Array<Boolean>>(
        arrayOf(true, true, false),
        arrayOf(false, false, false),
        arrayOf(false, true, true)
    )

    @Test
    fun getNeighbourhood() {
        val neighbourhood = space.getNeighbourhood(currentSolution)
        expectedNeighbours.forEach { a ->
            assert(neighbourhood.any { b ->
                a.contentEquals(b)
            })
        }
    }

    @Test
    fun getRandomNeighbour() {
        val neighbour = space.getRandomNeighbour(currentSolution)
        assert(expectedNeighbours.any {
            it.contentEquals(neighbour)
        })
    }
}