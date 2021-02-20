package space.d_lowl.kglsm.simulatedannealing.strategy

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import space.d_lowl.kglsm.general.memory.BasicCurrentState
import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.mock.DummySearchSpace
import space.d_lowl.kglsm.problem.CostFunction
import space.d_lowl.kglsm.simulatedannealing.memory.attribute.Temperature
import kotlin.math.absoluteValue

internal class TemperatureScheduleTest {
    class MockMemory(initialTemperature: Double) : Memory<Unit, BasicSolution<Unit>>(
            BasicCurrentState(BasicSolution<Unit>(arrayOf<Unit>(), 0.0)),
            BasicSolution<Unit>(arrayOf<Unit>(), 0.0),
            listOf(
                    Temperature(initialTemperature)
            )
    )

    private lateinit var memory: MockMemory
    private val searchSpace = DummySearchSpace()
    private val costFunction: CostFunction<Unit> = { 0.0 }

    @BeforeEach
    fun setUp() {
        memory = MockMemory(20.0)
    }

    @Test
    fun linearScheduleStep() {
        val schedule = LinearTemperatureSchedule<Unit>(0.1)
        schedule.step(memory, searchSpace, costFunction)
        assert(memory.getAttribute<Temperature>().temperature == 19.9)
    }

    @Test
    fun multiplicativeScheduleStep() {
        val schedule = MultiplicativeTemperatureSchedule<Unit>(0.9)
        schedule.step(memory, searchSpace, costFunction)
        assert(memory.getAttribute<Temperature>().temperature == 18.0)
    }

    @Test
    fun reciprocalScheduleStep() {
        val schedule = ReciprocalTemperatureSchedule<Unit>(0.1)
        schedule.step(memory, searchSpace, costFunction)
        assert((memory.getAttribute<Temperature>().temperature - 6.67).absoluteValue < 0.01)
    }
}