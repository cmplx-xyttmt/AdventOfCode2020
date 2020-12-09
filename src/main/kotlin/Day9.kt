import java.util.*

fun main() {
    val reader = InputReader("9")

    val numbers = mutableMapOf<Long, Int>()
    val preamble = 25
    fun isNumberValid(number: Long): Boolean {
        for ((n, _) in numbers) {
            val other = number - n
            if (other in numbers && other != n) return true
        }
        return false
    }

    val queue = ArrayDeque<Long>()
    var invalid = 0L
    val numList = mutableListOf<Long>()
    while (reader.hasNext()) {
        val number = reader.readLong()
        numList.add(number)
        if (queue.size == preamble) {
            val rem = queue.peekFirst()
            if (!isNumberValid(number)) {
                invalid = number
                println("Part 1: $number")
                break
            }
            queue.removeFirst()
            numbers[rem] = numbers[rem]?.minus(1) ?: 0
            if (numbers[rem] == 0) numbers.remove(rem)
        }
        queue.add(number)
        numbers[number] = numbers[number]?.plus(1) ?: 1
    }

    val prefixSum = numList.scan(0L) { acc, l -> acc + l }

    for (i in prefixSum.indices) {
        val next = prefixSum.binarySearch(prefixSum[i] + invalid)
        if (next >= 0) {
            val contiguous = numList.subList(i, next)
            val max = contiguous.maxOrNull()
            val min = contiguous.minOrNull()
            println("Part 2: ${max!! + min!!}")
            break
        }
    }
}
