package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.chebyshevDistance
import advent.calendar.aoc.solutions.utils.manhattanDistance
import advent.calendar.aoc.solutions.utils.nearby8

class Day09 : Solution<List<Pair<String, Int>>>() {
    override fun parse(lines: List<String>) = lines.map { val (a, b) = it.split(" "); a to b.toInt() }

    override fun part1(input: List<Pair<String, Int>>) = calc(input, 2)

    override fun part2(input: List<Pair<String, Int>>) = calc(input, 10)

    fun calc(input: List<Pair<String, Int>>, len: Int): Int {
        val positions = mutableSetOf<Point>()
        var segments = (1..len).map { Point(0, 0) }
        positions += segments
        for ((dir, dist) in input) {
            repeat(dist) {
                val shift = when (dir) {
                    "R" -> Point(1, 0)
                    "D" -> Point(0, -1)
                    "L" -> Point(-1, 0)
                    "U" -> Point(0, 1)
                    else -> TODO()
                }
                segments = segments.mapIndexed { i, p -> if (i > 0) p else p + shift }
                segments = updateSegments(segments)
                positions += segments.last()
            }
        }
        return positions.size
    }

    fun updateSegments(segment: List<Point>): List<Point> {
        val result = segment.toMutableList()
        for (i in 0 until result.size - 1) {
            val h = result[i]
            val t = result[i + 1]
            if (h chebyshevDistance t > 1) {
                result[i + 1] = h.nearby8().filter { it chebyshevDistance t <= 1 }.minBy { h manhattanDistance it }
            }
        }
        return result
    }
}
