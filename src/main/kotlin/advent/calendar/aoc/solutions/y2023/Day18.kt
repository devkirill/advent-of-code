package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.area2
import kotlin.math.abs

class Day18 : Solution<List<Day18.Line>>() {
    override fun parse(lines: List<String>) =
        lines.map { val (a, b, c) = it.split(" ");Line(a, b.toInt(), c.removePrefix("(#").removeSuffix(")")) }

    override fun part1(input: List<Line>) = calc(input)

    override fun part2(input: List<Line>) = calc(input.map {
        val dir = when (it.color[5]) {
            '0' -> "R"
            '1' -> "D"
            '2' -> "L"
            '3' -> "U"
            else -> TODO()
        }
        val dist = it.color.substring(0..4).toInt(16)
        it.copy(dir = dir, dist = dist)
    })

    fun calc(input: List<Line>): Long {
        var pos = Point.ZERO
        var sq = 0L
        for (l in input) {
            val shift = when (l.dir) {
                "R" -> Point(1, 0)
                "D" -> Point(0, 1)
                "L" -> Point(-1, 0)
                "U" -> Point(0, -1)
                else -> TODO()
            } * l.dist
            val np = pos + shift
            sq += area2(Point.ZERO, pos, np)
            pos = np
        }
        return abs(sq) / 2 + input.sumOf { it.dist } / 2 + 1
    }

    data class Line(val dir: String, val dist: Int, val color: String)
}
