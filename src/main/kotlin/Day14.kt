fun main() {
    val reader = InputReader("14")

    fun applyMask(num: Long, mask: String): Long {
        var numBin = num.toString(2)
        numBin = "0".repeat(mask.length - numBin.length) + numBin

        val result = CharArray(mask.length)
        for (i in mask.indices) {
            when {
                mask[i] == '0' -> result[i] = '0'
                mask[i] == '1' -> result[i] = '1'
                else -> result[i] = numBin[i]
            }
        }

        return result.joinToString("").toLong(2)
    }

    fun applyMaskToAddress(address: Long, mask: String): String {
        var addressBin = address.toString(2)
        addressBin = "0".repeat(mask.length - addressBin.length) + addressBin

        val result = CharArray(mask.length)
        for (i in mask.indices) {
            when {
                mask[i] == '0' -> result[i] = addressBin[i]
                mask[i] == '1' -> result[i] = '1'
                mask[i] == 'X' -> result[i] = 'X'
            }
        }

        return result.joinToString("")
    }

    val memory = mutableMapOf<Int, Long>()
    val memory2 = mutableMapOf<Long, Long>()

    fun writeToAllAddresses(addressMask: String, value: Long) {
        val xCount = addressMask.count { it == 'X' }
        val possible = (1 shl xCount)
        for (i in 0 until possible) {
            val address = addressMask.toCharArray()
            var currBit = 0
            for (j in address.indices) {
                if (address[j] == 'X') {
                    address[j] = if (i and (1 shl currBit) > 0) '1' else '0'
                    currBit++
                }
            }

            memory2[address.joinToString("").toLong(2)] = value
        }
    }

    var mask = ""
    while (reader.hasNext()) {
        val line = reader.readLn().split(" ")
        if (line[0] == "mask") mask = line[2]
        else {
            val address = line[0].substring(4, line[0].length - 1).toInt()
            val value = line[2].toLong()
            memory[address] = applyMask(value, mask)
            val aMask = applyMaskToAddress(address.toLong(), mask)
            writeToAllAddresses(aMask, value)
        }
    }

    println("Part 1: ${memory.values.sum()}")
    println("Part 2: ${memory2.values.sum()}")
}
