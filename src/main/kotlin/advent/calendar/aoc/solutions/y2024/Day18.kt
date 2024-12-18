package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.dijkstraSearch
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.contains
import advent.calendar.aoc.solutions.utils.geom.nearby4
import advent.calendar.aoc.solutions.utils.parse
import advent.calendar.aoc.solutions.utils.toInt

class Day18 : Solution<List<Point>>() {
    override fun parse(lines: List<String>) =
        lines.map { it.parse("(\\d+),(\\d+)".toRegex()) { it.toInt() }.let { (x, y) -> Point(x, y) } }

    fun solve(
        input: List<Point>,
        bytes: Int = (if (input.size > 1024) 1024 else 12),
        size: Int = (if (input.size > 1024) 70 else 6)
    ): Int? {
        val walls = input.take(bytes).toSet()
        val result =
            dijkstraSearch(listOf(Point(0, 0)), { it.nearby4().filter { it !in walls && it in 0..size } }, { 1 })
        return result[Point(size, size)]
    }

    override fun part1(input: List<Point>): Any {
        return solve(input)!!
    }

    override fun part2(input: List<Point>): Any {
        val res = input.indices.toList().binarySearchBy(0) {
            when {
                solve(input, it) != null -> -1
                solve(input, it - 1) == null -> 1
                else -> 0
            }
        }
        return input[res - 1]
    }
}
