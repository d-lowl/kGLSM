package space.d_lowl.kglsm.general.space

import kotlin.random.Random

fun Array<*>.isRepeating(): Boolean {
    this.forEachIndexed { index, element ->
        if (this.slice(0 until index).contains(element)) return true
    }
    return false
}

class PermutationSearchSpace(private val size: Int) : SearchSpace<Int> {
    override fun getInitial(): Array<Int> = Array(size) { it }
    override fun getRandomNeighbour(solution: Array<Int>): Array<Int> {
        val a = Random.nextInt(0, size - 1)
        val b = Random.nextInt(a + 1, size)
        return twoExchange(solution, a, b)
    }

    override fun getNeighbourhood(solution: Array<Int>): Sequence<Array<Int>> {
        return sequence {
            for (a in 0 until size - 1) {
                for (b in a + 1 until size) {
                    yield(twoExchange(solution, a, b))
                }
            }
        }
    }

    fun twoExchange(solution: Array<Int>, a: Int, b: Int): Array<Int> {
        return (solution.sliceArray(0 until a) +
                solution.sliceArray(a..b).reversed() +
                solution.sliceArray((b+1) until size))
    }

}