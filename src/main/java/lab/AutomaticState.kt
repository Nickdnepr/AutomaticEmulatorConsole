package lab

data class AutomaticState(val transactions:MutableMap<Char, String>, val isFinal:Boolean, val qualifier:String) {

    fun getNextQualifier(letter:Char) = transactions[letter]?:"NULL"

}