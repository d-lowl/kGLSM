package space.d_lowl.kglsm.evolutionaryalgorithms

import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Mutation
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.PushStash
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Recombination
import space.d_lowl.kglsm.evolutionaryalgorithms.strategy.Selection
import space.d_lowl.kglsm.general.memory.Memory
import space.d_lowl.kglsm.general.memory.PopulationSolution
import space.d_lowl.kglsm.sls.GLSM
import space.d_lowl.kglsm.sls.GLSMBuilder
import space.d_lowl.kglsm.sls.SequentialStateMachineBuilder
import space.d_lowl.kglsm.sls.StateMachine
import space.d_lowl.kglsm.general.transitionpredicate.TransitionPredicate

class GeneticAlgorithmBuilder<T> (
    recombination: Recombination<T>,
    mutation: Mutation<T>,
    selection: Selection<T>,
    terminationPredicate: TransitionPredicate<Memory<T, PopulationSolution<T>>>
) : GLSMBuilder<T, PopulationSolution<T>>()
{
    private val stateMachine: StateMachine<T, PopulationSolution<T>>
    init {
        val pushStash = PushStash<T>()
        stateMachine = SequentialStateMachineBuilder(terminationPredicate)
                .addStep(pushStash)
                .addStep(recombination)
                .addStep(pushStash)
                .addStep(mutation)
                .addStep(pushStash)
                .addStep(selection)
                .build()
    }

    override fun build(): GLSM<T, PopulationSolution<T>> = GLSM(stateMachine)
}