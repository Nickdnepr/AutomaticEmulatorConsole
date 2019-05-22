package lab.nfa

import stacklab.nickdnepr.com.automaticaemu.automaticaCore.Automatic
import stacklab.nickdnepr.com.automaticaemu.automaticaCore.AutomaticConstructor
import stacklab.nickdnepr.com.automaticaemu.automaticaCore.AutomaticState

object AutomaticDeterminer {

    fun determine(automatic: NonDeterminedAutomatic): Automatic {
        automatic.goToStart()
        val uniqueStates: MutableList<MutableList<NonDeterminedAutomaticState>> = mutableListOf(automatic.currentStates)
        val notProcessedStates: MutableList<MutableList<NonDeterminedAutomaticState>> = mutableListOf(automatic.currentStates)
        while (notProcessedStates.isNotEmpty()) {
            val inProcess = notProcessedStates.first()
            notProcessedStates.removeAt(0)
            automatic.alphabet.forEach { letter ->
                automatic.currentStates = inProcess
                val statesOnStep = automatic.testStep(letter)
                if (!uniqueStates.contains(statesOnStep) && statesOnStep.isNotEmpty()) {
                    notProcessedStates.add(statesOnStep)
                    uniqueStates.add(statesOnStep)
                    println("Adding $statesOnStep")
                }
            }
        }

        val compressedStates: MutableList<NonDeterminedAutomaticState> = mutableListOf()
        uniqueStates.forEach {
            val qualifier = StringBuilder()
            val isFinal = it.any { it.isFinal }
            it.forEach {
                qualifier.append(it.qualifier)
            }
            compressedStates.add(NonDeterminedAutomaticState(mutableListOf(), isFinal, qualifier.toString()))
        }
        println(compressedStates)
        val constructor = AutomaticConstructor.instance(AutomaticState(mutableMapOf(), automatic.startupState.isFinal, compressedStates.find { it.qualifier.contains(automatic.startupState.qualifier) }?.qualifier
                ?: throw NonDeterminedAutomatic.internalProcessingException))
        compressedStates.forEach {
            constructor.addState(AutomaticState(mutableMapOf(), it.isFinal, it.qualifier))
        }
        uniqueStates.forEach { stateList ->
            stateList.first().transactions.forEach { transaction ->

                constructor.addTransaction(compressedStates.find { it.qualifier.contains(stateList.first().qualifier) }?.qualifier
                        ?: throw NonDeterminedAutomatic.internalProcessingException, compressedStates.find { it.qualifier.contains(transaction.second) }?.qualifier
                        ?: throw NonDeterminedAutomatic.internalProcessingException,
                        transaction.first )
            }
        }
//        println(constructor.build(true))
        return constructor.build(true)
    }
}