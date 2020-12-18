fun main() {
    val reader = InputReader("17")

    fun simulateCycle(activeCubes: List<Cube>): List<Cube> {
        val nextCubes = mutableMapOf<Cube, CubeState>()
        activeCubes.forEach { cube -> nextCubes[cube] = CubeState(true) }
        var n = 0
        activeCubes.forEach { cube ->
            for (xDiff in -1..1) {
                for (yDiff in -1..1) {
                    for (zDiff in -1..1) {
                        if (!listOf(xDiff, yDiff, zDiff).all { it == 0 }) {
                            val neighbor = Cube(cube.x + xDiff, cube.y + yDiff, cube.z + zDiff)
                            if (neighbor !in nextCubes) nextCubes[neighbor] = CubeState()
                            nextCubes[neighbor]!!.neighbors++
                            n++
                        }
                    }
                }
            }
        }

        nextCubes.forEach { (_, cubeState) ->
            if (cubeState.active && cubeState.neighbors !in 2..3) cubeState.active = false
            else if (!cubeState.active && cubeState.neighbors == 3) cubeState.active = true
        }

        return nextCubes.filter { (_, cubeState) -> cubeState.active }.keys.toList()
    }

    fun simulateHyper(activeCubes: List<HyperCube>): List<HyperCube> {
        val nextCubes = mutableMapOf<HyperCube, CubeState>()
        activeCubes.forEach { cube -> nextCubes[cube] = CubeState(true) }
        activeCubes.forEach { cube ->
            for (xDiff in -1..1) {
                for (yDiff in -1..1) {
                    for (zDiff in -1..1) {
                        for (wDiff in -1..1) {
                            if (!listOf(xDiff, yDiff, zDiff, wDiff).all { it == 0 }) {
                                val neighbor = HyperCube(cube.x + xDiff, cube.y + yDiff,
                                        cube.z + zDiff, cube.w + wDiff)
                                if (neighbor !in nextCubes) nextCubes[neighbor] = CubeState()
                                nextCubes[neighbor]!!.neighbors++
                            }
                        }
                    }
                }
            }
        }

        nextCubes.forEach { (_, cubeState) ->
            if (cubeState.active && cubeState.neighbors !in 2..3) cubeState.active = false
            else if (!cubeState.active && cubeState.neighbors == 3) cubeState.active = true
        }

        return nextCubes.filter { (_, cubeState) -> cubeState.active }.keys.toList()
    }


    var x = 0
    val z = 0
    val w = 0
    var activeCubes = mutableListOf<Cube>()
    var activeHyperCubes = mutableListOf<HyperCube>()
    while (reader.hasNext()) {
        val row = reader.readLn()
        row.forEachIndexed { y, c ->
            if (c == '#') {
                activeCubes.add(Cube(x, y, z))
                activeHyperCubes.add(HyperCube(x, y, z, w))
            }
        }
        x++
    }

    repeat(6) {
        activeCubes = simulateCycle(activeCubes).toMutableList()
    }

    repeat(6) {
        activeHyperCubes = simulateHyper(activeHyperCubes).toMutableList()
    }

    println("Part 1: ${activeCubes.size}")
    println("Part 2: ${activeHyperCubes.size}")
}

data class Cube(val x: Int, val y: Int, val z: Int)

data class HyperCube(val x: Int, val y: Int, val z: Int, val w: Int)

data class CubeState(var active: Boolean = false, var neighbors: Int = 0)
