package stacklab.nickdnepr.com.automaticaemu.automaticaCore

import lab.nfa.NonDeterminedAutomatic

class AutomaticConstructor private constructor(private val automatic: Automatic) {

    private val alphabet = mutableListOf<Char>()

    fun addState(state: AutomaticState) {
        automatic.addState(state)
    }

    fun addTransaction(stateQualifier: String, transactionQualifier: String, letter: Char) {
        if (letter == NonDeterminedAutomatic.lambda) {
            return
        }
        println("Adding $stateQualifier to $transactionQualifier by $letter")
        val current =
                automatic.states[stateQualifier] ?: throw NullPointerException("State with $stateQualifier not found")
        current.transactions[letter] = transactionQualifier
        if (!alphabet.contains(letter)) {
            alphabet.add(letter)
        }
    }

    fun build(missingOnItself: Boolean = false): Automatic {
        automatic.states.values.forEach {
            if (alphabet.isEmpty()) {
                throw IllegalStateException("Automatic alphabet is empty")
            }

            alphabet.forEach { letter ->
                automatic.states.values.forEach { state ->
                    if (!state.transactions.keys.contains(letter)) {
                        if (missingOnItself) {
                            addTransaction(state.qualifier, state.qualifier, letter)
                        } else {
                            throw IllegalStateException("Automatic is not complete")
                        }
                    }
                }
            }
        }
        automatic.alphabet.addAll(alphabet)
        return automatic
    }

    fun removeTransaction(stateQualifier: String, letter: Char) {
        automatic.states[stateQualifier]?.transactions?.remove(letter)
    }

    fun removeState(stateQualifier: String) {
        automatic.states.remove(stateQualifier)
    }

    fun getAllQualifiers(list: MutableList<String>) {
        list.clear()
        list.addAll(automatic.states.keys)
    }

    override fun toString(): String {
        return "AutomaticConstructor(automatic=$automatic)"
    }


    companion object {
        fun instance(startPoint: AutomaticState) =
                AutomaticConstructor(Automatic(mutableMapOf(), startPoint, mutableListOf()))
    }
}