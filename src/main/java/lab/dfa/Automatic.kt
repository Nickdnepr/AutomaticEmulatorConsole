package stacklab.nickdnepr.com.automaticaemu.automaticaCore

data class Automatic(val states: MutableMap<String, AutomaticState>, val startupState: AutomaticState, val alphabet: MutableList<Char>) {

    var currentState = startupState

    init {
        states[currentState.qualifier] = currentState
    }

    fun step(letter: Char) {
        currentState = states[currentState.getNextQualifier(letter)] ?: throw IllegalStateException("State does not have condition for specified letter or qualified state does not exists")
    }

    fun goToState(qualifier: String) {
        currentState = states[qualifier] ?: currentState
    }

    fun addState(state: AutomaticState) {
        states[state.qualifier] = state
    }

    fun getAnswer() = currentState.isFinal
}