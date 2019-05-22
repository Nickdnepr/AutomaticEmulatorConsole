import lab.nfa.AutomaticDeterminer
import lab.nfa.NonDeterminedAutomatic
import lab.nfa.NonDeterminedAutomaticConstructor
import lab.nfa.NonDeterminedAutomaticState
import java.lang.Exception

fun main(args: Array<String>) {
    val helpStr = "type addS to add new state, type addC to add new condition, type help to see this message again, type reset to drop automatic, print det to convert nfa to dfa"
    println(helpStr)
    var constructor: NonDeterminedAutomaticConstructor? = null
    println("Enter the command")
    var command = readLine()
    loop@ while (command != "exit") {
        when (command) {
            "help" -> println(helpStr)
            "addS" -> {
                if (constructor == null) {
                    println("enter the initial state name")
                    val stateName = readLine() ?: "q1"
                    println("input if the initial state is final true/false")
                    val isFinal = try {
                        readLine()?.toBoolean() ?: false
                    } catch (e: Exception) {
                        println("invalid input, assigned false")
                        false
                    }
                    println("assigned $isFinal")
                    constructor = NonDeterminedAutomaticConstructor.instance(NonDeterminedAutomaticState(mutableListOf(), isFinal, stateName))
                } else {
                    println("enter the initial state name")
                    val stateName = readLine() ?: "q1"
                    println("input if the initial state is final true/false")
                    val isFinal = try {
                        readLine()?.toBoolean()?:false
                    } catch (e: Exception) {
                        println("invalid input, assigned false")
                        false
                    }
                    println("assigned $isFinal")
                    constructor.addState(NonDeterminedAutomaticState(mutableListOf(), isFinal, stateName))
                }
            }
            "addT" -> {
                if (constructor == null) {
                    println("add states first")
                } else {
                    println("Input source of transaction")
                    val stateName = readLine() ?: continue@loop

                    println("Input source of transaction")
                    val transName = readLine() ?: continue@loop

                    println("Input letter of transaction. @ stands for lambda")
                    val letter = readLine()?.get(0) ?: continue@loop
                    try {
                        constructor.addTransaction(stateName, transName, letter)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
            "reset" -> constructor = null
            "det" -> constructor?.build().apply { println("determined\n $this to \n${AutomaticDeterminer.determine(this!!)}") }?: println("Please input states and transactions")
        }
        println("Enter the command")
        command = readLine()
    }
}

