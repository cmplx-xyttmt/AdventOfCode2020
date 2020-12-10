fun main() {
    val reader = InputReader("10")

    val joltages = mutableListOf<Int>()

    while (reader.hasNext()) joltages.add(reader.readInt())

    joltages.sort()
    joltages.add(joltages.last() + 3)
    val diffs = Array(joltages.maxOrNull()!! + 1) { IntArray(4) }
    val reach = BooleanArray(diffs.size)
    reach[0] = true
    for (i in joltages.indices) {
        val jolt = joltages[i]
        for (j in 1..3) {
            if (jolt - j in diffs.indices && reach[jolt - j]) {
                for (diff in 1..3) diffs[jolt][diff] = diffs[jolt - j][diff]
                reach[jolt] = true
                diffs[jolt][j] += 1
                break
            }
        }
//        println("$jolt --> ${diffs[jolt].joinToString(" ")}")
    }

    println("Part 1: ${diffs.last()[1] * diffs.last()[3]}")
    reach.fill(false)
    reach[0] = true
    val ways = Array(reach.size) { LongArray(4) }
    ways[0][0] = 1L
    for (i in joltages.indices) {
        val jolt = joltages[i]
        for (j in 1..3) {
            if (jolt - j in reach.indices && reach[jolt - j]) {
                ways[jolt][j] = ways[jolt - j].sum()
                reach[jolt] = true
            }
        }
    }

    println("Part 2: ${ways.last().sum()}")
}
