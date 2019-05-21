import lab.nfa.NonDeterminedAutomatic
import lab.nfa.NonDeterminedAutomaticConstructor
import lab.nfa.NonDeterminedAutomaticState

fun main(args: Array<String>) {
    val constructor = NonDeterminedAutomaticConstructor.instance(NonDeterminedAutomaticState(mutableListOf(), false, "q0"))
    constructor.addState(NonDeterminedAutomaticState(mutableListOf(), false, "q1"))
    constructor.addState(NonDeterminedAutomaticState(mutableListOf(), false, "q2"))
    constructor.addState(NonDeterminedAutomaticState(mutableListOf(), false, "q3"))

    constructor.addTransaction("q0", "q1", NonDeterminedAutomatic.lambda)
    constructor.addTransaction("q1", "q2", NonDeterminedAutomatic.lambda)
    constructor.addTransaction("q2", "q3", NonDeterminedAutomatic.lambda)
    constructor.addTransaction("q3", "q1", NonDeterminedAutomatic.lambda)

    constructor.addTransaction("q0", "q1", 'A')

    val automatic = constructor.build()
    println(automatic.currentStates)
    automatic.step('A')
    println(automatic.currentStates)
    automatic.step('A')
    println(automatic.currentStates)
}

