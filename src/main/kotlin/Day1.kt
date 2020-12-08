fun main() {
    val reader = InputReader("1")
    val nums = mutableListOf<Int>()
    val sumToProduct = mutableMapOf<Int, Int>()
    // Part 2
    repeat(200) {
        val num = reader.readInt()
        if (2020 - num in sumToProduct) {
            println(sumToProduct[2020 - num]!! * num)
            return
        }
        for (n in nums) {
            sumToProduct[n + num] = n * num
        }
        nums.add(num)
    }
    println("Not found!!")
}
