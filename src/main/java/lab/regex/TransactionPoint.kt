package lab.regex

class TransactionPoint {

    val name = "q$index"

    init {
        index++
    }

    companion object {
        var index = 0
    }

    override fun toString(): String {
        return "TransactionPoint(name='$name')"
    }
}