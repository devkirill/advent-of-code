package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.manhattanDistance

class Day11 : Solution<List<Point>>() {
    override fun parse(lines: List<String>): List<Point> {
        val points = mutableListOf<Point>()
        for (y in lines.indices) {
            for (x in lines[0].indices) {
                val p = Point(x, y)
                when (lines[y][x]) {
                    '#' -> {
                        points += p
                    }

                    '.' -> {}
                    else -> TODO("${lines[y][x]}?")
                }
            }
        }
        return points
    }

    override fun part1(input: List<Point>) = calc(input, 2)

    override fun part2(input: List<Point>) = calc(input, 1000000)

    fun calc(input: List<Point>, scale: Long): Long {
        val xx = (0..input.maxOf { it.x }).filter { x -> input.none { it.x == x } }
        val yy = (0..input.maxOf { it.y }).filter { y -> input.none { it.y == y } }
        return input.sumOf { a ->
            input.sumOf { b ->
                (a manhattanDistance b).toLong() +
                        (a.x..b.x).count { it in xx } * (scale - 1) * 2 +
                        (a.y..b.y).count { it in yy } * (scale - 1) * 2
            }
        } / 2L
    }
}

