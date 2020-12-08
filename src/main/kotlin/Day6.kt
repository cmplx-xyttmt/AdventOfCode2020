fun main() {
    val reader = InputReader("6")
    val groups = mutableListOf<MutableSet<Char>>()
    groups.add(mutableSetOf())
    var new = true
    while (reader.hasNext()) {
        val line = reader.readLn()
        if (line.isEmpty()) {
            groups.add(mutableSetOf())
            new = true
        } else {
            if (new) groups.last().addAll(line.toCharArray().toList())
            else groups[groups.size - 1] = groups.last().intersect(line.toCharArray().toList()).toMutableSet()
            new = false
        }
    }
//    println(groups)
    println(groups.map { it.size }.sum())
}
