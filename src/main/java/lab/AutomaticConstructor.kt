package lab

import java.lang.NullPointerException

class AutomaticConstructor private constructor(private val automatic: Automatic){


    fun addState(state: AutomaticState){
        automatic.addState(state)
    }

    fun addTransaction(stateQualifier:String, transactionQualifier:String, letter:Char){
        val current =  automatic.states[stateQualifier]?:throw NullPointerException("State with $stateQualifier not found")
        current.transactions[letter] = transactionQualifier
    }

    fun build() = automatic

    fun removeTransaction(stateQualifier:String,letter:Char){
        automatic.states[stateQualifier]?.transactions?.remove(letter)
    }

    fun removeState(stateQualifier:String){
//        automatic.states[stateQualifier]?.transactions?.keys?.forEach{
//            removeTransaction(stateQualifier, it)
//        }
        automatic.states.remove(stateQualifier)
    }

    public companion object {
        fun instance(startPoint:AutomaticState) = AutomaticConstructor(Automatic(mutableMapOf(), startPoint))
    }
}