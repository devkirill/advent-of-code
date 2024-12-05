package advent.calendar.aoc.solutions.utils.geom

import kotlin.math.abs
import kotlin.math.max

data class Point(val x: Int, val y: Int) {
    operator fun plus(p: Point) = Point(x + p.x, y + p.y)
    operator fun minus(p: Point) = Point(x - p.x, y - p.y)
    operator fun times(a: Int) = Point(x * a, y * a)
    operator fun div(a: Int) = Point(x / a, y / a)

    val mannSize get() = x + y

    fun by(lambda: Builder.() -> Unit): Point {
        val builder = Builder(this)
        lambda(builder)
        return builder.build()
    }

    data class Builder(var x: Int, var y: Int) {
        constructor(p: Point) : this(p.x, p.y)

        fun build() = Point(x, y)
    }

    companion object {
        val ZERO = Point(0, 0)
    }
}

fun area2(a: Point, b: Point, c: Point) =
    (b.x.toLong() - a.x.toLong()) * (c.y.toLong() - a.y.toLong()) - (b.y.toLong() - a.y.toLong()) * (c.x.toLong() - a.x.toLong())

fun intersect(a: Point, b: Point, c: Point, d: Point): Boolean {
    fun intersect1(a: Int, b: Int, c: Int, d: Int) = max(Integer.min(a, b), Integer.min(c, d)) <= Integer.min(
        max(a, b),
        max(c, d)
    )

    return intersect1(a.x, b.x, c.x, d.x) &&
            intersect1(a.y, b.y, c.y, d.y) &&
            area2(a, b, c) * area2(a, b, d) <= 0 &&
            area2(c, d, a) * area2(c, d, b) <= 0
}

fun Point.nearby4() = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1)).map { this + it }
fun Point.nearby4Diag() = listOf(Point(-1, -1), Point(1, -1), Point(1, 1), Point(-1, 1)).map { this + it }
fun Point.around() = (-1..1)
    .flatMap { y -> (-1..1).map { x -> Point(x, y) } }
    .map { this + it }

fun Point.nearby8() = (-1..1)
    .flatMap { y -> (-1..1).map { x -> Point(x, y) } }
    .filter { it.x != 0 || it.y != 0 }
    .map { this + it }

infix fun Point.chebyshevDistance(p: Point) = (this - p).let { max(abs(it.x), abs(it.y)) }
infix fun Point.manhattanDistance(p: Point) = (this - p).let { abs(it.x) + abs(it.y) }
