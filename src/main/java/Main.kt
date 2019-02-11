import automatic.Automatic
import automatic.AutomaticState
import com.google.gson.Gson

lateinit var state1: AutomaticState
lateinit var state2: AutomaticState

fun main(args: Array<String>) {

    state1 = AutomaticState(mutableMapOf(), false, "state1")
    state2 = AutomaticState(mutableMapOf(), true, "state2")
    state1.transactions['0'] = "state1"
    state1.transactions['1'] = "state2"
    state1.transactions['2'] = "state3"
    state2.transactions['0'] = "state1"
    state2.transactions['1'] = "state1"

    val automatic = Automatic(mutableMapOf(Pair(state1.qualifier, state1), Pair(state2.qualifier, state2)), state1)
    automatic.step('1')
    println(automatic.currentState.qualifier)
    automatic.step('0')
    println(automatic.currentState.qualifier)
    automatic.step('1')
    println(automatic.currentState.qualifier)

    println(automatic.currentState.isFinal)

    println(Gson().toJson(automatic))
}

