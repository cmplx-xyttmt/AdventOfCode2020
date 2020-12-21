fun main() {
    val reader = InputReader("21")

    val allergensToIngredients = mutableMapOf<String, MutableSet<String>>()
    val ingredients = mutableListOf<List<String>>()
    val allergens = mutableListOf<List<String>>()
    while (reader.hasNext()) {
        val line = reader.readLn()
                .replace("(", "")
                .replace(")", "")
                .replace(",", "")
                .split(" ")
        var seenContains = false
        val ings = mutableListOf<String>()
        val allgs = mutableListOf<String>()
        for (word in line) {
            when {
                word == "contains" -> seenContains = true
                seenContains -> allgs.add(word)
                else -> ings.add(word)
            }
        }
        ingredients.add(ings)
        allergens.add(allgs)
    }

    fun filter(index: Int) {
        for (allergen in allergens[index]) {
            if (allergen in allergensToIngredients)
                allergensToIngredients[allergen] = allergensToIngredients[allergen]!!
                        .intersect(ingredients[index].toMutableSet()).toMutableSet()
            else allergensToIngredients[allergen] = ingredients[index].toMutableSet()
            if (allergensToIngredients[allergen]!!.size == 1) {
                val ingredient = allergensToIngredients[allergen]!!.first()
                for ((allg, ing) in allergensToIngredients) {
                    if (allg != allergen) {
                        ing.remove(ingredient)
                    }
                }
            }
        }
    }

    for (i in ingredients.indices) {
        filter(i)
    }

    val allergenIngredients = allergensToIngredients.values.flatten().toSet()
    println("Part 1: ${ingredients.flatten().filter { it !in allergenIngredients }.size}")

    val sortedByAllergen = allergensToIngredients
            .map { (allergen, ingredient) -> Pair(allergen, ingredient.first()) }
            .sortedBy { aIPair ->  aIPair.first }
            .map { pair -> pair.second }
    println("Part 2: ${sortedByAllergen.joinToString(",")}")
}