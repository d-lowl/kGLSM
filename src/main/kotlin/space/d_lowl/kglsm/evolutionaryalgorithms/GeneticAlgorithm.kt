package space.d_lowl.kglsm.evolutionaryalgorithms

import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Mutation
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.PushStash
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Recombination
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Selection
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.sls.SequentialStateMachineBuilder
import space.d_lowl.kglsm.sls.StateMachine
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate

object GeneticAlgorithm {
    fun <T> getStateMachine(
            recombination: Recombination<T>,
            mutation: Mutation<T>,
            selection: Selection<T>,
            terminationPredicate: TransitionPredicate<Memory<T, PopulationSolution<T>>>
    ): StateMachine<T, PopulationSolution<T>> {
        val pushStash = PushStash<T>()
        return SequentialStateMachineBuilder(terminationPredicate)
                .addStep("STASH_INIT", pushStash)
                .addStep("RECOMBINATION", recombination)
                .addStep("STASH_RECOMBINATION", pushStash)
                .addStep("MUTATION", mutation)
                .addStep("STASH_MUTATION", pushStash)
                .addStep("SELECTION", selection)
                .build()
    }
}