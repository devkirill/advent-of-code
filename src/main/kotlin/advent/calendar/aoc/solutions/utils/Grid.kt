package advent.calendar.aoc.solutions.utils

import advent.calendar.aoc.solutions.utils.geom.Point


data class Grid<T>(val size: Point, val data: Map<Point, T>, val default: (Point) -> T) {
    operator fun get(p: Point) = data[p] ?: default(p)

    val keys = data.keys

    fun find(value: T) = data.filterValues { value == it }.keys
    fun find(check: (T) -> Boolean) = data.filterValues { check(it) }.keys

    fun <G> map(transform: (T) -> G): Grid<G> {
        return Grid(
            size = size,
            data = data.mapValues { (_, v) -> transform(v) },
            default = { p -> transform(default(p)) })
    }

    operator fun contains(p: Point) = p in data

    override fun toString(): String {
        val builder = StringBuilder()
        for (row in 0 until size.y) {
            for (col in 0 until size.x) {
                builder.append(get(Point(col, row)).toString())
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    companion object {
        operator fun invoke(
            lines: List<String>,
            delimiter: String = "",
            default: (Point) -> String = { "" }
        ): Grid<String> {
            return Grid(
                Point(lines[0].length, lines.size),
                lines.flatMapIndexed { y, line ->
                    line.split(delimiter)
                        .let { if (delimiter == "") it.dropLast(1).drop(1) else it }
                        .mapIndexed { x, s -> Point(x, y) to s }
                }
                    .toMap(),
                default
            )
        }
    }
}