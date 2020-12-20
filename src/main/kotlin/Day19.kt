fun main() {
    val reader = InputReader("19")
    val messages = mutableListOf<String>()
    val originalRules = mutableMapOf<Int, Rule>()
    var readingRules = true
    while (reader.hasNext()) {
        val line = reader.readLn()
        if (line.isBlank()) readingRules = false
        else if (readingRules) {
            if (line.contains("\"")) {
                val (id, char) = line.split(": ")
                originalRules[id.toInt()] = Rule(id.toInt(), null, char.replace("\"", "")[0])
            } else {
                val list = line.split("|")
                val (id, firstRule) = list[0].split(": ")
                val options = (list.subList(1, list.size) + firstRule).map { r ->
                    r.trim().split(" ").map { it.toInt() }
                }
                originalRules[id.toInt()] = Rule(id.toInt(), options)
            }
        } else {
            messages.add(line)
        }
    }

    fun check(message: String, index: Int, rule: Rule, rules: Map<Int, Rule>): Pair<Boolean, Int> {
        if (rule.char != null) {
            if (index !in message.indices) return Pair(false, index)
            val matches = message[index] == rule.char
            return Pair(matches, if (matches) index + 1 else index)
        }

        for (option in rule.options!!) {
            val matches = option.fold(Pair(true, index)) match@{ acc, ruleId ->
                val res = check(message, acc.second, rules[ruleId]!!, rules)
                if (!res.first) return@match Pair(false, res.second)
                Pair(res.first && acc.first, res.second)
            }
            if (matches.first) return matches
        }

        return Pair(false, index)
    }

    fun matches(message: String, reps: Int): Boolean {
        val rules = originalRules.toMutableMap()
        for (rep8 in 1..reps) {
            for (rep11 in 1..reps) {
                if (message.length < rep8 + rep11 * 2) break
                rules[8] = Rule(8, listOf(List(rep8) { 42 }))
                rules[11] = Rule(11, listOf(List(rep11) { 42 } + List(rep11) { 31 }))
                val result = check(message, 0, rules[0]!!, rules)
                if (result.first && message.length == result.second) return true
            }
        }
        return false
    }

    val matching1 = messages.filter { matches(it, 1) }
    println("Part 1: ${matching1.size}")

    val maxReps = messages.maxByOrNull { it.length }!!.length/2
    println(maxReps)
    val matching2 = messages.filter { matches(it, maxReps) }
    println("Part 2: ${matching2.size}")
}

data class Rule(val id: Int, val options: List<List<Int>>? = null, val char: Char? = null)
