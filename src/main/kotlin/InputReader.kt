import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class InputReader(day: String) {

    // Read from file
    private val inputReader = InputStreamReader(FileInputStream(File("input/input${day}.txt")))
    private val lines = inputReader.readLines()
    private var currLine = -1
    fun readLn(): String {
        currLine++
        return lines[currLine]
    }

    fun hasNext(): Boolean {
        return currLine + 1 in lines.indices
    }

    fun readStrings() = readLn().trim().split(" ")

    fun readInt() = readLn().toInt()

    fun readInts() = readStrings().map { it.toInt() }

    fun readLong() = readLn().toLong()
}