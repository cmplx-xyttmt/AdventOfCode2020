import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {

    fun isPasswordValid(policy: String, letter: Char, password: String): Boolean {
        val (min, max) = policy.split("-").map { it.toInt() }
        val letterCount = password.count { it == letter }
        return letterCount in min..max
    }

    fun isPasswordValid2(policy: String, letter: Char, password: String): Boolean {
        val (i, j) = policy.split("-").map { it.toInt() }
        fun positionContains(pos: Int) = if (letter == password[pos - 1]) 1 else 0
        return positionContains(i) + positionContains(j) == 1
    }

    var valid1 = 0
    var valid2 = 0
    while (hasNext()) {
        val pw = readStrings()
        valid1 += if (isPasswordValid(pw[0], pw[1][0], pw[2])) 1 else 0 // Part 1
        valid2 += if (isPasswordValid2(pw[0], pw[1][0], pw[2])) 1 else 0 // Part 2
    }
    println("Part 1: ${valid1}\nPart 2: $valid2")
}

// Read from file
private val inputReader = InputStreamReader(FileInputStream(File("input/input2.txt")))
private val lines = inputReader.readLines()
private var currLine = -1
private fun hasNext(): Boolean {
    return currLine + 1 in lines.indices
}

private fun readLn(): String {
    currLine++
    return lines[currLine]
}

private fun readStrings() = readLn().trim().split(" ")
