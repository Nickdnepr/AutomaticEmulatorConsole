package stacklab.nickdnepr.com.automaticaemu.automaticaCore

class AutomaticConstructor private constructor(private val automatic: Automatic) {

    val alphabet = mutableListOf<Char>()

    fun addState(state: AutomaticState) {
        automatic.addState(state)
    }

    fun addTransaction(stateQualifier: String, transactionQualifier: String, letter: Char) {
        val current =
            automatic.states[stateQualifier] ?: throw NullPointerException("State with $stateQualifier not found")
        current.transactions[letter] = transactionQualifier
        if (!alphabet.contains(letter)) {
            alphabet.add(letter)
        }
    }

    fun build(): Automatic {
        automatic.states.values.forEach {

            if (alphabet.isEmpty()) {
                throw IllegalStateException("Automatic alphabet is empty")
            }

//            it.transactions.keys.forEach {
//                if (!alphabet.contains(it)){
//                    throw IllegalStateException("Automatic is not complete")
//                }
//            }
            alphabet.forEach { letter ->
                automatic.states.values.forEach { state ->
                    if (!state.transactions.keys.contains(letter)) {
                        throw IllegalStateException("Automatic is not complete")
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
//        automatic.states[stateQualifier]?.transactions?.keys?.forEach{
//            removeTransaction(stateQualifier, it)
//        }
        automatic.states.remove(stateQualifier)
    }

    fun getAllQualifiers(list: MutableList<String>) {
        list.clear()
        list.addAll(automatic.states.keys)
    }

    companion object {
        fun instance(startPoint: AutomaticState) =
            AutomaticConstructor(Automatic(mutableMapOf(), startPoint, mutableListOf()))
    }
}