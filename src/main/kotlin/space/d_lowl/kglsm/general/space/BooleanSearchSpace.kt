package space.d_lowl.kglsm.general.space

import kotlin.random.Random

class BooleanSearchSpace(private val noVariables: Int) : SearchSpace<Boolean> {
    override fun getNeighbourhood(solution: Array<Boolean>): Sequence<Array<Boolean>> =
        sequence {
            for (i in solution.indices) {
                val neighbour = solution.copyOf()
                neighbour[i] = !neighbour[i]
                yield(neighbour)
            }
        }

    override fun getRandomNeighbour(solution: Array<Boolean>): Array<Boolean> {
        val neighbour = solution.copyOf()
        val i = Random.nextInt(solution.size)
        neighbour[i] = !neighbour[i]
        return neighbour
    }

    override fun getInitial(): Array<Boolean> = Array(noVariables) { Random.nextBoolean() }
}