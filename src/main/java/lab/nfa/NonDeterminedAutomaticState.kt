package lab.nfa

class NonDeterminedAutomaticState(val transactions: MutableList<Pair<Char, String>>, val isFinal: Boolean, val qualifier: String) {

    override fun toString(): String {
        return "NonDeterminedAutomaticState(qualifier='$qualifier')"
    }
}