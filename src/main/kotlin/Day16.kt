fun main() {
    val reader = InputReader("16")

    val fields = mutableListOf<Field>()
    val myTicket = mutableListOf<Int>()
    val nearbyTickets = mutableListOf<List<Int>>()
    var part = 0
    while (reader.hasNext()) {
        val line = reader.readLn()
        if (line == "your ticket:" || line == "nearby tickets:") part++
        else if (line.isEmpty()) continue
        else if (part == 0) {
            val (field, range) = line.trim().split(":")
            val rangeStrings = range.trim().split("or")
            val ranges = rangeStrings.map { rangeStr ->
                val (from, to) = rangeStr.trim().split("-").map { it.toInt() }
                from..to
            }
            fields.add(Field(field, ranges))
        } else if (part == 1) myTicket.addAll(line.split(",").map { it.toInt() })
        else nearbyTickets.add(line.split(",").map { it.toInt() })
    }

    val allRanges = mutableListOf<IntRange>()

    fields.forEach { allRanges.addAll(it.ranges) }

    val errorRate = nearbyTickets.map { ticket ->
        ticket.filter { value ->
            !allRanges.any { value in it }
        }.sum()
    }.sum()

    println("Part 1: $errorRate")

    fun isTicketValid(ticket: List<Int>) = ticket.all { value -> allRanges.any { value in it } }

    val validTickets = nearbyTickets.filter { isTicketValid(it) }.toMutableList()
    validTickets.add(myTicket)

    val possiblePositions = mutableMapOf<String, MutableList<Int>>()

    for (field in fields) {
        for (i in validTickets[0].indices) {
            val allValues = validTickets.map { it[i] }
            val all = allValues.all { value -> field.ranges.any { range -> value in range } }
            if (all) {
                if (field.fieldName !in possiblePositions)
                    possiblePositions[field.fieldName] = mutableListOf()

                possiblePositions[field.fieldName]!!.add(i)
            }
        }
    }

    val fieldToPosition = mutableMapOf<String, Int>()
    val taken = mutableSetOf<Int>()
    while (possiblePositions.isNotEmpty()) {
        val minimum = possiblePositions.minByOrNull { it.value.size }!!

        fieldToPosition[minimum.key] = minimum.value.first()
        possiblePositions.remove(minimum.key)
        taken.add(fieldToPosition[minimum.key]!!)

        for (field in possiblePositions.keys) {
            possiblePositions[field] = possiblePositions[field]!!.filter { it !in taken }.toMutableList()
        }
    }

    var ans = 1L
    for ((field, position) in fieldToPosition) {
        if (field.contains("departure")) {
            ans *= myTicket[position]
        }
    }

    println("Part 2: $ans")
}

data class Field(val fieldName: String, val ranges: List<IntRange>)