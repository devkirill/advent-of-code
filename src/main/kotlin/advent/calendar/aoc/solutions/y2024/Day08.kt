package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.zipAllPairs

class Day08 : Solution<Grid>() {
    override fun parse(lines: List<String>) = Grid(lines) { "." }

    fun antinodes1(a: Point, b: Point, check: (Point) -> Boolean) = listOf(b * 2 - a, a * 2 - b).filter { check(it) }
    fun antinodes2(a: Point, b: Point, check: (Point) -> Boolean): List<Point> {
        fun get(a: Point, b: Point): List<Point> {
            val result = mutableListOf<Point>()
            val d = b - a
            var p = b
            while (check(p)) {
                result += p
                p += d
            }
            return result
        }
        return get(a, b) + get(b, a)
    }

    fun calc(input: Grid, part2: Boolean = false): Any {
        val keys = input.data.values.filter { it != "." && it != "#" }.distinct()
        val holes = mutableSetOf<Point>()
        for (key in keys) {
            input.find(key).zipAllPairs().forEach { (a, b) ->
                holes += if (!part2) antinodes1(a, b) { it in input } else antinodes2(a, b) { it in input }
            }
        }
        return holes.size
    }

    override fun part1(input: Grid) = calc(input)
    override fun part2(input: Grid) = calc(input, true)
}