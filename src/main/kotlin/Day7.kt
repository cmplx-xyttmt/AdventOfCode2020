import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {

    fun parseLine(line: String): Bag {
        val words = line.split(" ")
        val outerColor = "${words[0]} ${words[1]}"
        var i = 4
        val contents = mutableListOf<Content>()
        while (i < words.size) {
            val amount = if (words[i] == "no") 0 else words[i].toInt()
            if (amount != 0) {
                val color = "${words[i + 1]} ${words[i + 2]}"
                contents.add(Content(color, amount))
            }
            i += 4
        }
        return Bag(outerColor, contents)
    }

    val bags = mutableListOf<Bag>()
    val colorToIndex = mutableMapOf<String, Int>()
    while (hasNext()) {
        bags.add(parseLine(readLn()))
        colorToIndex[bags.last().color] = bags.size - 1
    }

    val seen = BooleanArray(bags.size)
    val hasShinyGold = BooleanArray(bags.size)
    fun dfs(index: Int): Boolean {
        seen[index] = true
        for (content in bags[index].contents) {
            val contentIndex = colorToIndex[content.color]!!
            if (content.color == "shiny gold") {
                hasShinyGold[index] = true
                break
            }
            if (!seen[contentIndex]) hasShinyGold[index] = hasShinyGold[index] || dfs(contentIndex)
            else hasShinyGold[index] = hasShinyGold[index] || hasShinyGold[contentIndex]
            if (hasShinyGold[index]) break
        }

        return hasShinyGold[index]
    }

    for (i in bags.indices) {
        if (!seen[i]) dfs(i)
    }

    println("Part 1: ${hasShinyGold.count { it }}")

    for (i in seen.indices) seen[i] = false
    val sizes = IntArray(bags.size)
    fun dfs2(index: Int): Int {
        seen[index] = true
        var totalBags = bags[index].contents.map { it.amount }.sum()
        for (content in bags[index].contents) {
            val contentIndex = colorToIndex[content.color]!!
            totalBags += content.amount * if (seen[contentIndex]) sizes[contentIndex] else dfs2(contentIndex)
        }
        sizes[index] = totalBags
        return sizes[index]
    }

    println("Part 2: ${dfs2(colorToIndex["shiny gold"]!!)}")
}


data class Bag(val color: String, val contents: List<Content>)

data class Content(val color: String, val amount: Int)

// Read from file
private val inputReader = InputStreamReader(FileInputStream(File("input/input7.txt")))
private val lines = inputReader.readLines()
private var currLine = -1
private fun readLn(): String {
    currLine++
    return lines[currLine]
}
private fun hasNext(): Boolean {
    return currLine + 1 in lines.indices
}
