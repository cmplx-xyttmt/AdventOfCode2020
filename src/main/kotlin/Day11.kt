fun main() {

    val reader = InputReader("11")

    fun change(i: Int, j: Int, grid: List<String>): Char {
        val occupied = grid[i][j] == '#'
        var neighbors = 0
        for (di in -1..1) {
            for (dj in -1..1) {
                if (i + di in grid.indices && j + dj in grid[i].indices && !(di == 0 && dj == 0)) {
                    neighbors += if (grid[i + di][j + dj] == '#') 1 else 0
                }
            }
        }

        if (!occupied && neighbors == 0) return '#'
        if (occupied && neighbors >= 4) return 'L'
        return grid[i][j]
    }

    fun countNeighbors(i: Int, j: Int, grid: List<String>): Int {
        var neighbors = 0
        for (di in -1..1) {
            for (dj in -1..1) {
                if (i + di in grid.indices && j + dj in grid[i].indices && !(di == 0 && dj == 0)) {
                    var r = i + di
                    var c = j + dj
                    while (r in grid.indices && c in grid[i].indices) {
                        if (grid[r][c] == '#') {
                            neighbors++
                            break
                        }
                        if (grid[r][c] == 'L') break
                        r += di
                        c += dj
                    }
                }
            }
        }
        return neighbors
    }

    fun change2(i: Int, j: Int, grid: List<String>): Char {
        val occupied = grid[i][j] == '#'
        val neighbors = countNeighbors(i, j, grid)
        if (!occupied && neighbors == 0) return '#'
        if (occupied && neighbors >= 5) return 'L'
        return grid[i][j]
    }

    fun simulate(inputGrid: List<String>, part: Int = 1): List<String> {
        val newGrid = List(inputGrid.size) { CharArray(inputGrid[0].length) }

        for (i in inputGrid.indices) {
            for (j in inputGrid[0].indices) {
                if (inputGrid[i][j] == '.') newGrid[i][j] = '.'
                else newGrid[i][j] = if (part == 1) change(i, j, inputGrid) else change2(i, j, inputGrid)
            }
        }

        return newGrid.map { it.joinToString("") }
    }

    val inputGrid = mutableListOf<String>()
    while (reader.hasNext()) inputGrid.add(reader.readLn())

    var curr = inputGrid.toList()
    while (true) {
        val next = simulate(curr)
        if (next == curr) break
        curr = next
    }

    println("Part 1: " + curr.map { row -> row.count { it == '#' } }.sum())

    curr = inputGrid.toList()

    while (true) {
        val next = simulate(curr, 2)
        if (next == curr) break
        curr = next
    }

    println("Part 2: " + curr.map { row -> row.count { it == '#' } }.sum())
}
