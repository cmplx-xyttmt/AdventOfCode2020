fun main() {
    val reader = InputReader("2")
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
    while (reader.hasNext()) {
        val pw = reader.readStrings()
        valid1 += if (isPasswordValid(pw[0], pw[1][0], pw[2])) 1 else 0 // Part 1
        valid2 += if (isPasswordValid2(pw[0], pw[1][0], pw[2])) 1 else 0 // Part 2
    }
    println("Part 1: ${valid1}\nPart 2: $valid2")
}
