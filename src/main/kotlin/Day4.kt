fun main() {
    val reader = InputReader("4")
    fun isValidDate(date: String, minYear: Int, maxYear: Int): Boolean {
        if (date.length != 4) return false
        val intDate = date.toIntOrNull() ?: return false
        return intDate in minYear..maxYear
    }

    fun validHeight(hgt: String): Boolean {
        if (hgt.length < 3) return false
        val value = hgt.substring(0, hgt.length - 2).toIntOrNull() ?: return false
        val units = hgt.substring(hgt.length - 2)
        return (units == "cm" && value in 150..193) || (units == "in" && value in 59..76)
    }

    fun validHCL(hcl: String): Boolean {
        val length = hcl.length
        if (length != 7) return false
        if (hcl[0] != '#') return false

        return hcl.substring(1).map { (it - '0' in 0..9) || (it - 'a' in 0..5) }.all { it }
    }

    fun validECL(ecl: String): Boolean {
        val valid = mutableSetOf("amb", "blu", "brn", "gry", "hzl", "oth", "grn")
        return ecl in valid
    }

    fun validPID(pid: String): Boolean {
        return pid.length == 9 && pid.toIntOrNull() != null
    }

    fun isValidPassport(fields: Map<String, String>): Boolean {
        val allPresent = fields.size == 8 || (fields.size == 8 - 1 && "cid" !in fields)
        if (!allPresent) return false
        val byr = isValidDate(fields["byr"] ?: error(""), 1920, 2002)
        val iyr = isValidDate(fields["iyr"] ?: error(""), 2010, 2020)
        val eyr = isValidDate(fields["eyr"] ?: error(""), 2020, 2030)
        val hgt = validHeight(fields["hgt"] ?: error(""))
        val hcl = validHCL(fields["hcl"] ?: error(""))
        val ecl = validECL(fields["ecl"] ?: error(""))
        val pid = validPID(fields["pid"] ?: error(""))

        return (byr && iyr && eyr && hgt && hcl && ecl && pid)
    }
//    val fields = 8
    var validPassports = 0
    var currFields = mutableMapOf<String, String>()
    while (reader.hasNext()) {
        val line = reader.readLn()
        if (line.isEmpty()) {
            if (isValidPassport(currFields)) validPassports++

            currFields = mutableMapOf()
        } else {
            val fields = line.trim().split(" ").map { it.split(":") }
//            println(fields)
            fields.forEach { (field, value) -> currFields[field] = value }
        }
    }
    if (isValidPassport(currFields)) validPassports++
    println(validPassports)
}
