package stacklab.nickdnepr.com.automaticaemu.automaticaCore

object AutomaticMinimizer {

    fun minimize(automatic: Automatic) :Automatic{
        val finalStates = mutableListOf<AutomaticState>()
        val nonFinalStates = mutableListOf<AutomaticState>()
        automatic.states.values.forEach {
            if (it.isFinal) {
                finalStates.add(it)
            } else {
                nonFinalStates.add(it)
            }
        }
        var groups = mutableListOf(finalStates, nonFinalStates)
        var newGroups = splitStateGroups(groups)
        while (groups != newGroups) {
            groups = newGroups
            newGroups = splitStateGroups(groups)
        }
        println(groups.toString())
        val compressedStates = mutableListOf<AutomaticState>()
        groups.forEach {
            val qualifierBuilder = StringBuilder()
            var isFinal = false
            it.forEach { state ->
                qualifierBuilder.append(state.qualifier)
                if (!isFinal) isFinal = state.isFinal
            }
            compressedStates.add(AutomaticState(mutableMapOf(), isFinal, qualifierBuilder.toString()))
        }

        val constructor = AutomaticConstructor.instance(compressedStates.find { compressedStateContainsState(it, automatic.startupState) }
                ?: throw IllegalStateException("Something went wrong"))
        compressedStates.forEach {
            constructor.addState(it)
        }

        groups.forEach {
            it.first().run {
                transactions.keys.forEach { letter ->
                    compressedStates.forEach { compressedState ->
                        if (compressedState.qualifier.contains(transactions[letter]!!)) {
                            constructor.addTransaction(compressedStates.find { compressedStateContainsState(it, this) }!!.qualifier, compressedState.qualifier, letter)
                        }
                    }
                }
            }
        }
        return constructor.build()
    }

    private fun splitStateGroups(groups: MutableList<MutableList<AutomaticState>>): MutableList<MutableList<AutomaticState>> {
        val newGroups = mutableListOf<MutableList<AutomaticState>>()
        groups.forEach { stateToSplit ->
            splitStateGroup(stateToSplit, groups).forEach {
                newGroups.add(it)
            }
        }
        return newGroups
    }

    private fun splitStateGroup(
            group: MutableList<AutomaticState>,
            groups: MutableList<MutableList<AutomaticState>>
    ): ArrayList<MutableList<AutomaticState>> {
        val newGroups = mutableMapOf<String, MutableList<AutomaticState>>()

        group.forEach { state ->
            val conditioning = StringBuilder()
            groups.forEach { internalGroup ->
                state.transactions.keys.forEach { letter ->
                    if (internalGroup.find { it.qualifier == state.transactions[letter] } != null) {
                        conditioning.append(groups.indexOf(internalGroup))
                    }
                }
            }
            newGroups[conditioning.toString()]?.let { it.add(state) } ?: kotlin.run {
                newGroups[conditioning.toString()] =
                        mutableListOf(state)
            }
        }
        return ArrayList(newGroups.values)
    }

    private fun groupsAreSplittable(groups: MutableList<MutableList<AutomaticState>>): Boolean {
        throw NotImplementedError()
    }

    private fun compressedStateContainsState(compressedState: AutomaticState, state: AutomaticState): Boolean = compressedState.qualifier.contains(state.qualifier)
}