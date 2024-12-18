package advent.calendar.aoc.solutions.utils.geom

data class Vector2d(val x: Double, val y: Double) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    operator fun plus(v: Vector2d) = Vector2d(x + v.x, y + v.y)
    operator fun minus(v: Vector2d) = Vector2d(x - v.x, y - v.y)
    operator fun times(t: Double) = Vector2d(x * t, y * t)
    operator fun div(t: Double) = Vector2d(x / t, y / t)
}