package lab.regex

object PatternSplitter {

    fun parse(expression: String) {
        var notSplittable = false
        val explanation = StringBuilder()
        toBrackets(expression).forEach {

            toUnions(it).forEach {
                explanation.append(" $it ")
            }
            explanation.append(" -> ")
        }
        println(explanation.toString())
    }

    fun toBrackets(expression: String): MutableList<String> {
        var expressions = mutableListOf<String>()
        if (expression.isEmpty()) return expressions
        if (!expression.contains("(")) return mutableListOf(expression)
        if (expression[0] != '(') {
            expressions.add(expression.substring(0, expression.indexOf('(')))
            expressions.addAll(toBrackets(expression.substring(expression.indexOf('('))).filter { it.isNotEmpty() })
            return expressions
        } else {
            var openingBrackets = 1
            var endIndex = 1
            expression.substring(1).forEach {
                endIndex++
                if (it == '(') openingBrackets++
                if (it == ')') {
                    openingBrackets--
                    if (openingBrackets == 0) {
                        expressions.add(expression.substring(1, endIndex - 1))
                        expressions.addAll(toBrackets(expression.substring(endIndex)).filter { it.isNotEmpty() })
                        return expressions
                    }
                }
            }
        }
        return expressions
    }

    fun toUnions(expression: String): MutableList<String> {
//        println("splitting $expression")
        val expressions = mutableListOf<String>()
        var bracketsNumber = 0
        expression.forEach {
            if (it == '(') bracketsNumber++
            if (it == ')') bracketsNumber--
            if (it == '|') {
                if (bracketsNumber == 0) {
                    expressions.add(expression.substring(0, expression.indexOf(it)))
                    expressions.addAll(toUnions(expression.substring(expression.indexOf(it) + 1)))
                    return expressions
                }
            }
        }

        expressions.add(expression.filter { it != '(' && it != ')' })
        return expressions
    }
}