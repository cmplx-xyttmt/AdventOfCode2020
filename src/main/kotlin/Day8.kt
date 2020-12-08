fun main() {
    val reader = InputReader("8")
    val instructions = mutableListOf<Instruction>()

    fun terminates(instructions: List<Instruction>): Pair<Boolean, Int> {
        var curr = 0
        val done = mutableSetOf<Int>()
        var accumulator = 0
        fun executeInstruction(instruction: Instruction, curr: Int): Int {
            return when (instruction.type) {
                "nop" -> curr + 1
                "acc" -> {
                    accumulator += instruction.value
                    curr + 1
                }
                "jmp" -> curr + instruction.value
                else -> 0
            }
        }
        while (curr < instructions.size && curr !in done) {
            done.add(curr)
            val instruction = instructions[curr]
            curr = executeInstruction(instruction, curr)
        }
        return Pair(curr == instructions.size, accumulator)
    }

    while (reader.hasNext()) {
        val inst = reader.readStrings()
        instructions.add(Instruction(inst[0], inst[1].toInt()))
    }

    val p1 = terminates(instructions)
    println("Part 1: ${p1.second}")

    val change = mutableMapOf(
            "jmp" to "nop",
            "nop" to "jmp"
    )
    for (i in instructions.indices) {
        val instruction = instructions[i]
        if (instruction.type in change) {
            val mod = instructions.subList(0, i).toMutableList()
            mod.add(Instruction(change[instruction.type]!!, instruction.value))
            mod.addAll(instructions.subList(i + 1, instructions.size))
            val p2 = terminates(mod)
            if (p2.first) {
                println("Part 2: ${p2.second}")
            }
        }
    }

}

data class Instruction(val type: String, val value: Int)