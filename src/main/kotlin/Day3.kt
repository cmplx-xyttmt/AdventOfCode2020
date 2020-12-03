import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    val grid = mutableListOf<String>()
    while (hasNext()) grid.add(readLn())
    val m = grid[0].length

    fun countTrees(right: Int, down: Int): Int {
        var i = 0
        var j = 0
        var trees = 0
        while (i < grid.size) {
            trees += if (grid[i][j] == '#') 1 else 0
            i += down
            j = (j + right) % m
        }
        return trees
    }

    println("Part 1: ${countTrees(3, 1)}")

    val part2Params = mutableListOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    val ans = part2Params.map { countTrees(it.first, it.second).toLong() }.reduce { acc, t -> acc * t }
    println("Part 2: $ans")
}

// Read from file
private val inputReader = InputStreamReader(FileInputStream(File("input/input3.txt")))
private val lines = inputReader.readLines()
private var currLine = -1
private fun hasNext(): Boolean {
    return currLine + 1 in lines.indices
}
private fun readLn(): String {
    currLine++
    return lines[currLine]
}
