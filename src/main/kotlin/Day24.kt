fun main() {
    val reader = InputReader("24")

    fun moveInDirection(position: Pair<Int, Int>, direction: String): Pair<Int, Int> {
        return when (direction) {
            "e" -> Pair(position.first + 2, position.second)
            "w" -> Pair(position.first - 2, position.second)
            "ne" -> Pair(position.first + 1, position.second + 2)
            "se" -> Pair(position.first + 1, position.second - 2)
            "nw" -> Pair(position.first - 1, position.second + 2)
            else -> Pair(position.first - 1, position.second - 2)
        }
    }

    val directions = mutableListOf<List<String>>()
    while (reader.hasNext()) {
        val line = reader.readLn().trim()
        var i = 0
        val direction = mutableListOf<String>()
        while (i < line.length) {
            if (line[i] == 'w' || line[i] == 'e') {
                direction.add(line.substring(i, i + 1))
                i++
            } else {
                direction.add(line.substring(i, i + 2))
                i += 2
            }
        }
        directions.add(direction)
    }

    var black = mutableSetOf<Pair<Int, Int>>()
    for (direction in directions) {
        val coord = direction.fold(Pair(0, 0)) { acc, s -> moveInDirection(acc, s) }
        if (coord in black) black.remove(coord)
        else black.add(coord)
    }

    println("Part 1: ${black.size}")

    var pairToNeighbors = mutableMapOf<Pair<Int, Int>, Int>()
    fun fillNeighbors(position: Pair<Int, Int>) {
        val neighbors = listOf("e", "w", "se", "ne", "nw", "sw").map { moveInDirection(position, it) }
        neighbors.forEach { neighbor -> pairToNeighbors[neighbor] = pairToNeighbors[neighbor]?.plus(1) ?: 1 }
    }

    val reps = 100
    repeat(reps) {
        pairToNeighbors = mutableMapOf()
        black.forEach {
            if (it !in pairToNeighbors) pairToNeighbors[it] = 0
            fillNeighbors(it)
        }
        val nextBlack = mutableSetOf<Pair<Int, Int>>()
        pairToNeighbors.forEach { (position, neighbors) ->
            if (position in black && neighbors in 1..2) nextBlack.add(position)
            if (position !in black && neighbors == 2) nextBlack.add(position)
        }
        black = nextBlack
    }

    println("Part 2: ${black.size}")
}