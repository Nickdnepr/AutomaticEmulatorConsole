package stacklab.nickdnepr.com.automaticaemu.automaticaCore

data class AutomaticState(val transactions:MutableMap<Char, String>, val isFinal:Boolean, val qualifier:String) {

    fun getNextQualifier(letter:Char) = transactions[letter]

    override fun toString(): String {
        return "AutomaticState(transactions=$transactions, qualifier='$qualifier')"
    }
}