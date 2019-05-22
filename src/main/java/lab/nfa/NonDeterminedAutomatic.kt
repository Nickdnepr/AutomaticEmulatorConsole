package lab.nfa

class NonDeterminedAutomatic(val states: MutableMap<String, NonDeterminedAutomaticState>, val startupState: NonDeterminedAutomaticState, val alphabet: MutableList<Char>) {

    var currentStates = mutableListOf(startupState)

    init {
        states[startupState.qualifier] = startupState
        startupState.transactions.forEach {
            if (it.first == lambda) {
                if (!currentStates.contains(states[it.second])) {
                    currentStates.add(states[it.second] ?: throw internalProcessingException)
                }
            }
        }
    }

    fun testStep(letter: Char): MutableList<NonDeterminedAutomaticState> {
        val save = ArrayList(currentStates)
        step(letter)
        val new = ArrayList(currentStates)
        currentStates = save
        return new
    }

    fun step(letter: Char) {
        if (currentStates.isEmpty()) {
            currentStates.add(startupState)
            return
        }
        val newCurrentStates = mutableListOf<NonDeterminedAutomaticState>()
        currentStates.forEach { state ->
            state.transactions.filter { it.first == letter }.forEach { transaction ->
                if (!newCurrentStates.contains(states[transaction.second])) {
                    newCurrentStates.add(states[transaction.second] ?: throw internalProcessingException)
                }
            }
        }
        currentStates = newCurrentStates
        var prevStates = ArrayList(currentStates)
        reviewForLambdas()
        while (prevStates != currentStates) {
            prevStates = ArrayList(currentStates)
            reviewForLambdas()
        }
    }

    fun getAnswer() = currentStates.any { it.isFinal }

    fun addState(state: NonDeterminedAutomaticState) {
        states[state.qualifier] = state
    }

    fun goToStart(){
        currentStates.clear()
        currentStates.add(startupState)
        var prevStates = ArrayList(currentStates)
        reviewForLambdas()
        while (prevStates != currentStates) {
            prevStates = ArrayList(currentStates)
            reviewForLambdas()
        }
    }

    fun reviewForLambdas() {
        val newCurrentStates = mutableListOf<NonDeterminedAutomaticState>()
        currentStates.forEach { state ->
            state.transactions.filter { it.first == lambda }.forEach { transaction ->
                if (!newCurrentStates.contains(states[transaction.second]) && !currentStates.contains(states[transaction.second])) {
                    newCurrentStates.add(states[transaction.second] ?: throw internalProcessingException)
                }
            }
        }
        currentStates.addAll(newCurrentStates)
    }

    override fun toString(): String {
        return "NonDeterminedAutomatic(states=$states, alphabet=$alphabet)"
    }


    companion object {
        const val lambda = '@'
        val internalProcessingException = IllegalStateException("Invalid qualifier found")
    }
}