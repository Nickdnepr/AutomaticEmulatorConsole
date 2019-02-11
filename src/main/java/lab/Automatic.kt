package lab

data class Automatic(val states: MutableMap<String,AutomaticState>, private val startupState: AutomaticState) {

    var currentState = startupState

    init {
        states[currentState.qualifier]=currentState
    }

    fun step(letter: Char) {
        currentState = states.getOrDefault(currentState.getNextQualifier(letter), currentState)
    }

    fun goToState(qualifier:String){
        currentState = states.getOrDefault(qualifier, currentState)
    }

    fun addState(state: AutomaticState){
        states[state.qualifier] = state
    }


}