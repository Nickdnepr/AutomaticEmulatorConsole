package automatic

data class Automatic(private val states: MutableMap<String,AutomaticState>, private val startupState: AutomaticState) {

    var currentState = startupState

    fun step(letter: Char) {
        currentState = states.getOrDefault(currentState.getNextQualifier(letter), currentState)
    }

    fun goToState(qualifier:String){
        currentState = states.getOrDefault(qualifier, currentState)
    }


}