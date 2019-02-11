import lab.Automatic
import lab.AutomaticState
import lab.AutomaticConstructor
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

//    val automatic = Automatic(mutableMapOf(Pair(state1.qualifier, state1), Pair(state2.qualifier, state2)), state1)
//    automatic.step('1')
//    println(automatic.currentState.qualifier)
//    automatic.step('0')
//    println(automatic.currentState.qualifier)
//    automatic.step('1')
//    println(automatic.currentState.qualifier)
//
//    println(automatic.currentState.isFinal)
//
//    println(Gson().toJson(automatic))
//
    val constructor = AutomaticConstructor.instance(AutomaticState(mutableMapOf(), false, "Start"))
//    constructor.addState()
    constructor.addState(AutomaticState(mutableMapOf(), false, "state1"))
    constructor.addState(AutomaticState(mutableMapOf(), false, "state2"))
    constructor.addState(AutomaticState(mutableMapOf(), false, "state3"))
    constructor.addState(AutomaticState(mutableMapOf(), false, "state4"))
    constructor.addState(AutomaticState(mutableMapOf(), true, "End"))

    constructor.addTransaction("Start", "state1", '0')
    constructor.addTransaction("Start", "state2", '1')

    constructor.addTransaction("state1", "state2", '0')
    constructor.addTransaction("state1", "state3", '1')

    constructor.addTransaction("state2", "state1", '0')
    constructor.addTransaction("state2", "state3", '1')

    constructor.addTransaction("state3", "state4", '0')
    constructor.addTransaction("state3", "state2", '1')

    constructor.addTransaction("state4", "state1", '0')
    constructor.addTransaction("state4", "End", '1')
    val end = constructor.build()

    println(end.currentState.qualifier)
    end.step('0')
    println(end.currentState.qualifier)
    end.step('0')
    println(end.currentState.qualifier)
    end.step('1')
    println(end.currentState.qualifier)
    end.step('0')
    println(end.currentState.qualifier)
    end.step('1')
    println(end.currentState.qualifier)

}

