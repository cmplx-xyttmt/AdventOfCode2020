fun main() {
    val reader = InputReader("5")
    val binMap = mutableMapOf(
            'F' to 0,
            'B' to 1,
            'L' to 0,
            'R' to 1,
    )
    fun convertToNum(s: String): Int {
        return s.map { binMap[it]!! }.joinToString("").toInt(2)
    }

    fun boardingPassToID(s: String): Int {
        val row = convertToNum(s.substring(0, 7))
        val col = convertToNum(s.substring(7))
        return row * 8 + col
    }

    val passes = mutableListOf<Int>()
    while (reader.hasNext()) passes.add(boardingPassToID(reader.readLn()))
    println("Part 1: ${passes.maxOrNull()}")

    passes.sort()

    for (i in 0 until passes.size - 1) {
        if (passes[i] + 1 != passes[i + 1]) {
            println("Part 2: ${passes[i] + 1}")
        }
    }
}
