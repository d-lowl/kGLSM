package space.d_lowl.kglsm.evolutionaryalgorithms

import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Mutation
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.PushStash
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Recombination
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Selection
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate
import space.d_lowl.kglsm.sls.StateMachine
import space.d_lowl.kglsm.sls.sequentialStateMachine

object GeneticAlgorithm {
    fun <T> getStateMachine(
            recombination: Recombination<T>,
            mutation: Mutation<T>,
            selection: Selection<T>,
            terminationPredicate: TransitionPredicate<Memory<T, PopulationSolution<T>>>
    ): StateMachine<T, PopulationSolution<T>> {
        val pushStash = PushStash<T>()
        return sequentialStateMachine {
            step {
                name = "STASH_INIT"
                strategy = pushStash
            }
            step {
                name = "RECOMBINATION"
                strategy = recombination
            }
            step {
                name = "STASH_RECOMBINATION"
                strategy = pushStash
            }
            step {
                name = "MUTATION"
                strategy = mutation
            }
            step {
                name = "STASH_MUTATION"
                strategy = pushStash
            }
            step {
                name = "SELECTION"
                strategy = selection
            }
            this.terminationPredicate = terminationPredicate
        }
    }
}