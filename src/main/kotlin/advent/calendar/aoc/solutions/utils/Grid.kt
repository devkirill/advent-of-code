package advent.calendar.aoc.solutions.utils

import advent.calendar.aoc.solutions.utils.geom.Point


data class Grid(val size: Point, val data: Map<Point, String>, val default: (Point) -> String = { "" }) {
    operator fun get(p: Point) = data[p] ?: default(p)

    val keys = data.keys

    fun find(value: String) = data.filterValues { value == it }.keys
    fun find(check: (String) -> Boolean) = data.filterValues { check(it) }.keys

    companion object {
        operator fun invoke(lines: List<String>, delimiter: String = "", default: (Point) -> String = { "" }): Grid {
            return Grid(
                Point(lines[0].length, lines.size),
                lines.flatMapIndexed { y, line -> line.split(delimiter).mapIndexed { x, s -> Point(x, y) to s } }
                    .toMap(),
                default
            )
        }
    }
}