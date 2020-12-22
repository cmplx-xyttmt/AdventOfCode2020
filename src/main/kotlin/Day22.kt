fun main() {
    val reader = InputReader("22")

    val player1 = mutableListOf<Int>()
    val player2 = mutableListOf<Int>()
    var currPlayer = 0
    while (reader.hasNext()) {
        val line = reader.readLn()
        if (line.isEmpty()) continue
        when {
            line.contains("Player") -> currPlayer++
            currPlayer == 1 -> player1.add(line.toInt())
            else -> player2.add(line.toInt())
        }
    }

    fun playGame(p1: ArrayDeque<Int>, p2: ArrayDeque<Int>): List<Int> {
        while (!(p1.isEmpty() || p2.isEmpty())) {
            if (p1.first() > p2.first()) {
                p1.addLast(p1.removeFirst())
                p1.addLast(p2.removeFirst())
            } else {
                p2.addLast(p2.removeFirst())
                p2.addLast(p1.removeFirst())
            }
        }

        return if (p1.isNotEmpty()) p1.toList() else p2.toList()
    }

    var p1Deque = ArrayDeque<Int>()
    p1Deque.addAll(player1)
    var p2Deque = ArrayDeque<Int>()
    p2Deque.addAll(player2)

    fun calcScore(finalList: List<Int>) = finalList.reversed()
        .foldIndexed(0) { index, acc, i -> acc + (index + 1) * i }

    val part1 = playGame(p1Deque, p2Deque)
    println("Part 1: ${calcScore(part1)}")

    fun recursiveCombat(p1: ArrayDeque<Int>, p2: ArrayDeque<Int>): Pair<Boolean, List<Int>> {
        val states = mutableSetOf<State>()
        while (!(p1.isEmpty() || p2.isEmpty())) {
            val currState = State(p1.toList(), p2.toList())
            if (states.contains(currState)) return Pair(true, p1.toList())
            states.add(currState)
            val p1Win = if (p1.size > p1.first() && p2.size > p2.first()) {
                val p1Rec = p1.toList().subList(1, p1.first() + 1)
                val p2Rec = p2.toList().subList(1, p2.first() + 1)
                val p1RecDeque = ArrayDeque<Int>()
                val p2RecDeque = ArrayDeque<Int>()
                p1RecDeque.addAll(p1Rec)
                p2RecDeque.addAll(p2Rec)
                val result = recursiveCombat(p1RecDeque, p2RecDeque)
                result.first
            } else {
                p1.first() > p2.first()
            }
            if (p1Win) {
                p1.addLast(p1.removeFirst())
                p1.addLast(p2.removeFirst())
            } else {
                p2.addLast(p2.removeFirst())
                p2.addLast(p1.removeFirst())
            }
        }
        return if (p1.isNotEmpty()) Pair(true, p1.toList()) else Pair(false, p2.toList())
    }

    p1Deque = ArrayDeque()
    p1Deque.addAll(player1)
    p2Deque = ArrayDeque()
    p2Deque.addAll(player2)

    val part2 = recursiveCombat(p1Deque, p2Deque)
    println("Part 2: ${calcScore(part2.second)}")
}

data class State(val p1: List<Int>, val p2: List<Int>)
