fun main() {
    val reader = InputReader("20")

    val tiles = mutableListOf<Tile>()
    while (reader.hasNext()) {
        val id = reader.readLn().trim().replace(":", "").split(" ")[1].toLong()
        var line = reader.readLn()
        val grid = mutableListOf<String>()
        while (line.isNotBlank()) {
            grid.add(line)
            if (!reader.hasNext()) break
            line = reader.readLn()
        }
        tiles.add(Tile(id, grid))
    }

    val edgesToIds = mutableMapOf<String, MutableList<Long>>()

    for (tile in tiles) {
        for (edge in tile.edges + tile.reversedEdges) {
            if (edge !in edgesToIds) edgesToIds[edge] = mutableListOf()
            edgesToIds[edge]?.add(tile.id)
        }
    }
//    println(tiles[0])
//    println("${tiles.size} ${edgesToIds.size}")
//    println(edgesToIds.values.filter { it.size == 1 }.size)
    val corners = edgesToIds.values
            .filter { it.size == 1 }
            .map { it[0] }
            .groupBy { it }
            .filter { it.value.size == 4 }.keys
    println("Part 1: ${corners.reduce { acc, l -> acc * l }}")

    // This solution for part 1 is based on a lot of assumptions, so I'm not happy with it.
    // TODO: Do part 2 later
    // Check out these resources for solutions when stuck:
    // https://www.reddit.com/r/adventofcode/comments/kgo01p/2020_day_20_solutions/
    // https://www.youtube.com/watch?v=H0rTE9r9YmQ
    //
}

data class Tile(val id: Long, val grid: List<String>) {
    private val top = grid.first()
    private val right = grid.map { it.last() }.joinToString("")
    private val bottom = grid.last()
    private val left = grid.map { it.first() }.joinToString("")
    val edges = mutableListOf(top, right, bottom, left)
    val reversedEdges = mutableListOf(top.reversed(), right.reversed(), bottom.reversed(), left.reversed())

    override fun toString(): String {
        return "$id\n${grid.joinToString("\n")}" +
                "\nTop->$top\nRight->$right\nBottom->$bottom\nLeft->$left"
    }
}
