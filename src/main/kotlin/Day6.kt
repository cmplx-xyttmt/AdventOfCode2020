import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    val groups = mutableListOf<MutableSet<Char>>()
    groups.add(mutableSetOf())
    var new = true
    while (hasNext()) {
        val line = readLn()
        if (line.isEmpty()) {
            groups.add(mutableSetOf())
            new = true
        } else {
            if (new) groups.last().addAll(line.toCharArray().toList())
            else groups[groups.size - 1] = groups.last().intersect(line.toCharArray().toList()).toMutableSet()
            new = false
        }
    }
//    println(groups)
    println(groups.map { it.size }.sum())
}

//// Input Reader
//private fun readLn() = readLine()!!
// Read from file
private val inputReader = InputStreamReader(FileInputStream(File("input/input6.txt")))
private val lines = inputReader.readLines()
private var currLine = -1
private fun readLn(): String {
    currLine++
    return lines[currLine]
}
private fun hasNext(): Boolean {
    return currLine + 1 in lines.indices
}
