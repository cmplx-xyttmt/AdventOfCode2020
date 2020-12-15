fun main() {
    val input = mutableMapOf(
            "test" to mutableListOf(0, 3, 6),
            "prod" to mutableListOf(0, 13, 1, 8, 6, 15)
    )
    var starting = input["prod"]!!

    var lastSeen = mutableMapOf<Int, Int>()

    starting.forEachIndexed { index, num -> lastSeen[num] = index }

    var next = 0
    while (starting.size < 2020) {
        val prevPos  = lastSeen[next] ?: starting.size
        starting.add(next)
        lastSeen[next] = starting.size - 1
        next = lastSeen[next]!! - prevPos
    }

    println("Part 1: ${starting.last()}")

    // Part 2
    next = 0
    starting = input["prod"]!!
    val limit = 30000000
    var size = starting.size
    lastSeen = mutableMapOf()
    starting.forEachIndexed { index, num -> lastSeen[num] = index }

    while (true) {
        val prevPos  = lastSeen[next] ?: size
        size++
        if (size == limit) break
        lastSeen[next] = size - 1
        next = lastSeen[next]!! - prevPos
    }

    println("Part 2: ${next}")
}
