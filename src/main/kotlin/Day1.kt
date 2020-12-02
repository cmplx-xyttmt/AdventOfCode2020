import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    val nums = mutableListOf<Int>()
    val sumToProduct = mutableMapOf<Int, Int>()
    // Part 2
    repeat(200) {
        val num = readInt()
        if (2020 - num in sumToProduct) {
            println(sumToProduct[2020 - num]!! * num)
            return
        }
        for (n in nums) {
            sumToProduct[n + num] = n * num
        }
        nums.add(num)
    }
    println("Not found!!")
}

// Read from file
private val inputReader = InputStreamReader(FileInputStream(File("input/input1.txt")))
private val lines = inputReader.readLines()
private var currLine = -1
private fun readLn(): String {
    currLine++
    return lines[currLine]
}

private fun readInt() = readLn().toInt()
