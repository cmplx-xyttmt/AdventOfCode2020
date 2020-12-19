import java.util.*

fun main() {
    val reader = InputReader("18")

    val expressions = mutableListOf<List<String>>()
    while (reader.hasNext()) expressions.add(reader.readLn().split(" "))

    fun p1(c: Char): Int {
        if (c == '*' || c == '+') return 0
        return -1
    }

    fun p2(c: Char): Int {
        if (c == '*') return 0
        if (c == '+') return 1
        return -1
    }

    fun evaluateExpression(expression: List<String>, precedence: (Char) -> Int): Long {
        val values = Stack<Long>()
        val operators = Stack<Char>()
        fun performOp() {
            val op = operators.pop()
            if (op == '+') values.push(values.pop() + values.pop())
            else values.push(values.pop() * values.pop())
        }
        expression.forEach { exp ->
            if (exp.first() == '(') {
                val opening = exp.count { it == '(' }
                repeat(opening) {
                    operators.push('(')
                }
                values.push(exp.substring(opening).toLong())
            } else if (exp.first() == '+' || exp.first() == '*') {
                while (operators.isNotEmpty() && precedence(exp.first()) <= precedence(operators.peek()))
                    performOp()
                operators.push(exp.first())
            } else if (exp.last() == ')') {
                var closing = exp.count { it == ')' }
                val value = exp.substring(0, exp.length - closing).toLong()
                values.push(value)
                while (closing > 0) {
                    if (operators.peek() == '(') {
                        operators.pop()
                        closing--
                    } else performOp()
                }
            } else {
                values.push(exp.toLong())
            }
        }

        while (operators.isNotEmpty()) performOp()

        return values.pop()
    }

    println("Part 1: ${expressions.map { evaluateExpression(it, ::p1) }.sum()}")
    println("Part 2: ${expressions.map { evaluateExpression(it, ::p2) }.sum()}")
}
