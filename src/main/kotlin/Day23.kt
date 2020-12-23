fun main() {
//    val input = "389125467".split("").filter { it.isNotEmpty() }
    val input = "394618527".split("").filter { it.isNotEmpty() }
//    println(input)
    fun makeMove(currCup: String, currConfig: List<String>): Pair<String, List<String>> {
        val n = currConfig.size
        val currIndex = currConfig.indexOf(currCup)
        val three = mutableListOf<String>()
        val rest = mutableListOf<String>()
        val threeIndices = (1..3).map { (currIndex + it) % n }
        three.addAll(threeIndices.map { currConfig[it] })
        for (i in currConfig.indices) {
            if (i !in threeIndices) rest.add(currConfig[i])
        }
//        println("${currConfig[currIndex]} $three")
//        println(rest)
        val lowest = rest.minOrNull()!!
        var target = currConfig[currIndex].toInt() - 1
        if (target < lowest.toInt()) {
            val highest = rest.withIndex().maxByOrNull { it.value }!!
            rest.addAll((highest.index + 1) % n, three)
            val newCup = rest[(rest.indexOf(currCup) + 1) % n]
            return Pair(newCup, rest)
        }
        while (target.toString() in three) {
            target -= 1
            if (target < lowest.toInt()) {
                val highest = rest.withIndex().maxByOrNull { it.value }!!
                rest.addAll((highest.index + 1) % n, three)
                val newCup = rest[(rest.indexOf(currCup) + 1) % n]
                return Pair(newCup, rest)
            }
        }
//        println(target)
        val targetIndex = rest.indexOf(target.toString())
        rest.addAll((targetIndex + 1) % n, three)
        val newCup = rest[(rest.indexOf(currCup) + 1) % n]
        return Pair(newCup, rest)
    }
    var currCup = input[0]
    var config = input
    repeat(100) {
        val next = makeMove(currCup, config)
        currCup = next.first
        config = next.second
    }

    val oneIndex = config.indexOf("1")
    val ans = config.subList(oneIndex, config.size) + config.subList(0, oneIndex)
    println("Part 1: ${ans.subList(1, ans.size).joinToString("")}")

    var valueToNode = mutableMapOf<Int, Node>()

    fun makeMove(currCup: Node, maxNum: Int): Node {
        val three = mutableSetOf<Int>()
        var node = currCup.next!!
        val threeNodeFirst = node
        val threeNodeLast = node.next!!.next!!
        currCup.next = threeNodeLast.next

        repeat(3) {
            three.add(node.value)
            node = node.next!!
        }
        val nextFourTargets = (1..4).map { d -> currCup.value - d }
        var target = nextFourTargets.first { it !in three }
        if (target < 1) target = (maxNum downTo maxNum - 4).first { it !in three }

        val targetNode = valueToNode[target]!!
        val targetNext = targetNode.next
        targetNode.next = threeNodeFirst
        threeNodeLast.next = targetNext
        return currCup.next!!
    }

    val values = input.map { it.toInt() }.toMutableList()
    for (i in values.indices) {
        val value = values[i]
        valueToNode[value] = Node(value)
        if (i > 0) valueToNode[values[i - 1]]!!.next = valueToNode[value]
    }
    valueToNode[values.last()]!!.next = valueToNode[values.first()]

    var currentCup = valueToNode[values.first()]!!
    repeat(100) {
        currentCup = makeMove(currentCup, 9)
    }
    val ans2 = mutableListOf<Int>()
    var node = valueToNode[1]!!.next!!
    while (node.value != 1) {
        ans2.add(node.value)
        node = node.next!!
    }
    println("Part 1(Second method): ${ans2.joinToString("")}")

    // Part 2
    valueToNode = mutableMapOf()
    val maximum = (1e6).toInt()
    values.addAll((10..maximum))
    val reps = (1e7).toInt()
    for (i in values.indices) {
        val value = values[i]
        valueToNode[value] = Node(value)
        if (i > 0) valueToNode[values[i - 1]]!!.next = valueToNode[value]
    }
    valueToNode[values.last()]!!.next = valueToNode[values.first()]

    currentCup = valueToNode[values.first()]!!
    repeat(reps) {
        currentCup = makeMove(currentCup, maximum)
    }

    val first = valueToNode[1]!!.next!!
    val second = first.next!!
    println("Part 2: ${first.value.toLong() * second.value}")
}

data class Node(val value: Int) {
    var next: Node? = null
}
