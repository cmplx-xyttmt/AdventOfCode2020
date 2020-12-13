import kotlin.math.ceil

fun main() {
    val reader = InputReader("13")

    val earliest = reader.readInt()
    val buses = reader.readLn().split(",")
    val activeBuses = buses.filter { it != "x" }.map { it.toInt() }

    val earliestActive = activeBuses.map { Pair(it, ceil(earliest.toDouble()/it).toInt() * it - earliest) }

    val bus = earliestActive.minByOrNull { it.second }!!
    println("Part 1: ${bus.first * bus.second}")

    fun modPow(x: Long, n: Int, m: Long): Long {
        if (n == 0) return 1 % m
        var u = modPow(x, n / 2, m)
        u = (u * u) % m
        if (n % 2 == 1) u = (u * (x % m)) % m
        return u
    }

    fun modInverse(x: Long, m: Long): Long {
        return modPow(x, m.toInt() - 2, m)
    }

    fun chineseRemainderTheorem(equations: List<Pair<Long, Long>>): Long {
        val x = equations.map { it.second }.reduce { acc, l -> acc * l }
        val xs = equations.map { x/it.second }
        return equations.mapIndexed { index, (a, m) ->
            a * xs[index] * modInverse(xs[index], m)
        }.reduce { acc, l -> acc + l } % x
    }

    val equations = mutableListOf<Pair<Long, Long>>()
    buses.forEachIndexed { index, busNum ->
        if (busNum != "x") {
            val b = busNum.toLong()
            equations.add(Pair((b - (index % b)) % b, busNum.toLong()))
        }
    }

    println("Part 2: ${chineseRemainderTheorem(equations)}")
}
