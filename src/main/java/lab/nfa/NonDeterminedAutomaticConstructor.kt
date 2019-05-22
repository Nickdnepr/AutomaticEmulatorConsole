package lab.nfa

class NonDeterminedAutomaticConstructor private constructor(private val automatic: NonDeterminedAutomatic) {

    private val alphabet = mutableListOf<Char>()

    fun addState(state: NonDeterminedAutomaticState) {
        automatic.states[state.qualifier] = state
    }

    fun addTransaction(stateQualifier: String, transactionQualifier: String, letter: Char) {
        val current =
                automatic.states[stateQualifier] ?: throw NullPointerException("State with $stateQualifier not found")
        current.transactions.add(Pair(letter, transactionQualifier))
        automatic.reviewForLambdas()
        if (!alphabet.contains(letter) && letter != NonDeterminedAutomatic.lambda) {
            alphabet.add(letter)
        }
    }

    fun build(): NonDeterminedAutomatic {
        automatic.alphabet.addAll(alphabet)
        return automatic
    }

    companion object {
        fun instance(startupState: NonDeterminedAutomaticState) = NonDeterminedAutomaticConstructor(NonDeterminedAutomatic(mutableMapOf(), startupState, mutableListOf()))
    }
}