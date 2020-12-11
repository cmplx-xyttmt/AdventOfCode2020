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
        // left
        for (dj in 1 until grid[i].length) {
            if (j - dj !in grid[i].indices) break
            if (grid[i][j - dj] == 'L') break
            if (grid[i][j - dj] == '#') {
                neighbors++
                break
            }
        }
        // right
        for (dj in 1 until grid[i].length) {
            if (j + dj !in grid[i].indices) break
            if (grid[i][j + dj] == 'L') break
            if (grid[i][j + dj] == '#') {
                neighbors++
                break
            }
        }
        // up
        for (di in 1 until grid.size) {
            if (i - di !in grid.indices) break
            if (grid[i - di][j] == 'L') break
            if (grid[i - di][j] == '#') {
                neighbors++
                break
            }
        }
        // down
        for (di in 1 until grid.size) {
            if (i + di !in grid.indices) break
            if (grid[i + di][j] == 'L') break
            if (grid[i + di][j] == '#') {
                neighbors++
                break
            }
        }
        // top right
        for (d in 1 until grid.size) {
            if (i - d !in grid.indices || j + d !in grid[0].indices) break
            if (grid[i - d][j + d] == 'L') break
            if (grid[i - d][j + d] == '#') {
                neighbors++
                break
            }
        }
        // top left
        for (d in 1 until grid.size) {
            if (i - d !in grid.indices || j - d !in grid[0].indices) break
            if (grid[i - d][j - d] == 'L') break
            if (grid[i - d][j - d] == '#') {
                neighbors++
                break
            }
        }
        // bottom right
        for (d in 1 until grid.size) {
            if (i + d !in grid.indices || j + d !in grid[0].indices) break
            if (grid[i + d][j + d] == 'L') break
            if (grid[i + d][j + d] == '#') {
                neighbors++
                break
            }
        }
        // bottom left
        for (d in 1 until grid.size) {
            if (i + d !in grid.indices || j - d !in grid[0].indices) break
            if (grid[i + d][j - d] == 'L') break
            if (grid[i + d][j - d] == '#') {
                neighbors++
                break
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
//    simulate(curr, 2)
    while (true) {
        val next = simulate(curr, 2)
        if (next == curr) break
        curr = next
    }

    println("Part 2: " + curr.map { row -> row.count { it == '#' } }.sum())
}
