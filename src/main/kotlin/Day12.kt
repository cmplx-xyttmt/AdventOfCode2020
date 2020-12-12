import kotlin.math.abs

fun main() {
    val reader = InputReader("12")

    val moves = mutableListOf<String>()

    while (reader.hasNext()) moves.add(reader.readLn())

    fun processMove(position: Position, move: String) {
        val action = move[0]
        val value = move.substring(1).toInt()
        val direction = value / 90
        when (action) {
            'N' -> position.y += value
            'S' -> position.y -= value
            'E' -> position.x += value
            'W' -> position.x -= value
            'L' -> position.direction = (position.direction + direction) % 4
            'R' -> position.direction = (4 + position.direction - direction) % 4
            'F' -> {
                when (position.direction) {
                    0 -> position.x += value
                    1 -> position.y += value
                    2 -> position.x -= value
                    3 -> position.y -= value
                }
            }
        }
    }

    val position = Position(0, 0, 0)
    moves.forEach { processMove(position, it) }
    println("Part 1: ${abs(position.x) + abs(position.y)}")

    fun rotateWayPoint(ship: Position, waypoint: Position, action: Char, direction: Int) {
        var x = 0
        var y = 0
        val xDiff = waypoint.x - ship.x
        val yDiff = waypoint.y - ship.y
        val anticlockwise = if (action == 'L') 1 else -1
        when (direction) {
            1 -> {
                x = ship.x + (-anticlockwise) * yDiff
                y = ship.y + (anticlockwise) * xDiff
            }
            2 -> {
                x = ship.x - xDiff
                y = ship.y - yDiff
            }
            3 -> {
                x = ship.x + (anticlockwise) * yDiff
                y = ship.y + (-anticlockwise) * xDiff
            }
        }
        waypoint.x = x
        waypoint.y = y
    }

    fun process2(ship: Position, waypoint: Position, move: String) {
        val action = move[0]
        val value = move.substring(1).toInt()
        val direction = value / 90
        when (action) {
            'L', 'R' -> rotateWayPoint(ship, waypoint, action, direction)
            'F' -> {
                val xDiff = waypoint.x - ship.x
                val yDiff = waypoint.y - ship.y
                ship.x += value * xDiff
                ship.y += value * yDiff
                waypoint.x = xDiff + ship.x
                waypoint.y = yDiff + ship.y
            }
            else -> processMove(waypoint, move)
        }
    }

    val ship = Position(0, 0, 0)
    val waypoint = Position(10, 1, 0)

    moves.forEach { process2(ship, waypoint, it) }
    println("Part 2: ${abs(ship.x) + abs(ship.y)}")
}

data class Position(var x: Int, var y: Int, var direction: Int)
