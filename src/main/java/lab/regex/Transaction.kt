package lab.regex

import lab.nfa.NonDeterminedAutomatic

data class Transaction(val source: TransactionPoint, val destination: TransactionPoint, val transaction: String) {

    fun split(): MutableList<Transaction> {
        if (transaction.length==1){
            return mutableListOf(this)
        }
        val newTransactions = mutableListOf<Transaction>()
        val transactions: MutableList<String>
        if (PatternSplitter.toUnions(transaction).size == 1 && transaction.any { it == '(' || it == ')' }) {
            transactions = PatternSplitter.toBrackets(transaction)
            if (transactions.size==1){
                return mutableListOf(this)
            }
            for (i in 0 until transactions.size) {
                if (i == 0) {
                    newTransactions.add(Transaction(source, TransactionPoint(), transactions[i]))
                    continue
                }
                if (i != transactions.size) {
                    newTransactions.add(Transaction(newTransactions.last().destination, destination, transactions[i]))
                    continue
                }
                newTransactions.add(Transaction(newTransactions.last().destination, TransactionPoint(), transactions[i]))
            }
        } else {
            if (transaction.contains('|')) {
                transactions = PatternSplitter.toUnions(transaction)
                if (transactions.size==1){
                    return mutableListOf(this)
                }
                transactions.forEach {
                    newTransactions.add(Transaction(source, destination, it))
                }
            } else {
                if (transaction.length == 2 && transaction[1] == '*') {
                    if (transaction.contains(NonDeterminedAutomatic.lambda)){
                        return  mutableListOf(this)
                    }
                    val point = TransactionPoint()
                    newTransactions.add(Transaction(source, point, "${NonDeterminedAutomatic.lambda}"))
                    newTransactions.add(Transaction(point, point, "${transaction[0]}"))
                    newTransactions.add(Transaction(point, destination, "${NonDeterminedAutomatic.lambda}"))
                } else {
                    val builder = StringBuilder()
                    for (i in 0 until transaction.length) {
                        if (transaction[i] == '*'||transaction[i]==NonDeterminedAutomatic.lambda) {
                            continue
                        }
                        if (i + 1 < transaction.length && transaction[i + 1] == '*') {
                            builder.append("(${transaction[i]}${transaction[i + 1]})")
                        } else {
                            builder.append("(${transaction[i]})")
                        }
                    }
//                    println("Rec")
                    return Transaction(source, destination, builder.toString()).split()
                }
            }
        }
        return newTransactions
    }

    override fun toString(): String {
        return "${source.name}->${destination.name}:$transaction"
    }
}