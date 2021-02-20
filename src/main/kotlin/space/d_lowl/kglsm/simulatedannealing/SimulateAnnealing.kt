package space.d_lowl.kglsm.simulatedannealing

import space.d_lowl.kglsm.general.memory.BasicSolution
import space.d_lowl.kglsm.general.transitionpredicate.NotPredicate
import space.d_lowl.kglsm.general.transitionpredicate.UnconditionalPredicate
import space.d_lowl.kglsm.simulatedannealing.strategy.AnnealingStep
import space.d_lowl.kglsm.simulatedannealing.strategy.ResetTemperature
import space.d_lowl.kglsm.simulatedannealing.strategy.TemperatureSchedule
import space.d_lowl.kglsm.simulatedannealing.transitionpredicate.TemperatureLowerPredicate
import space.d_lowl.kglsm.sls.StateMachine
import space.d_lowl.kglsm.sls.stateMachine

object SimulatedAnnealing {
    fun <T> getStateMachine(initialTemperature: Double, temperatureSchedule: TemperatureSchedule<T>): StateMachine<T, BasicSolution<T>> {
        val reset = ResetTemperature<T>(initialTemperature)
        val step = AnnealingStep<T>()
        val terminationPredicate = TemperatureLowerPredicate(0.0)
        val continuePredicate = NotPredicate(terminationPredicate)

        return stateMachine {
            entrypoint = "RESET"
            node {
                name = "RESET"
                strategy = reset
                transition {
                    to = "SIMANNEAL_STEP"
                    transitionPredicate = UnconditionalPredicate()
                }
            }
            node {
                name = "SIMANNEAL_STEP"
                strategy = step
                transition {
                    to = "TEMPERATURE_SCHEDULE"
                    transitionPredicate = UnconditionalPredicate()
                }
            }
            node {
                name = "TEMPERATURE_SCHEDULE"
                strategy = temperatureSchedule
                transition {
                    to = "SIMANNEAL_STEP"
                    transitionPredicate = continuePredicate
                }
                transition {
                    to = StateMachine.TERMINATION_STATE_LABEL
                    transitionPredicate = terminationPredicate
                }
            }
        }

    }
}

