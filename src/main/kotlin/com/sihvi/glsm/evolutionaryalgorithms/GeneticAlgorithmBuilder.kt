package com.sihvi.glsm.evolutionaryalgorithms

import com.sihvi.glsm.memory.Memory
import com.sihvi.glsm.memory.PopulationSolution
import com.sihvi.glsm.sls.GLSM
import com.sihvi.glsm.sls.GLSMBuilder
import com.sihvi.glsm.sls.SequentialStateMachineBuilder
import com.sihvi.glsm.sls.StateMachine
import com.sihvi.glsm.space.SearchSpace
import com.sihvi.glsm.transitionpredicate.TransitionPredicate

class GeneticAlgorithmBuilder<T>(recombination: Recombination<T>, mutation: Mutation<T>, selection: Selection<T>, terminationPredicate: TransitionPredicate<Memory<T, PopulationSolution<T>>>) : GLSMBuilder<T, PopulationSolution<T>, Memory<T, PopulationSolution<T>>, SearchSpace<T>>()
{
    private val stateMachine: StateMachine<T, PopulationSolution<T>, Memory<T, PopulationSolution<T>>, SearchSpace<T>>
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

    override fun build(): GLSM<T, PopulationSolution<T>, Memory<T, PopulationSolution<T>>, SearchSpace<T>> = GLSM(stateMachine)
}