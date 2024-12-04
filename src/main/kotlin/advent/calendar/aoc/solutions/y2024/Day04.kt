package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby8

class Day04 : Solution<Map<Point, Char>>() {
    override fun parse(lines: List<String>) =
        lines.flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }.toMap().withDefault { '.' }

    override fun part1(input: Map<Point, Char>): Int {
        val shifts = Point(0, 0).nearby8()
        return input.keys
            .flatMap { p ->
                shifts.map { s -> (0 until 4).map { input.getValue(p + s * it) }.joinToString("") }
            }
            .filter { it == "XMAS" }
            .size
    }

    override fun part2(input: Map<Point, Char>): Any {
        val mas = listOf("MS", "SM")
        return input.keys
            .filter {
                input.getValue(it) == 'A' &&
                        "${input.getValue(it + Point(-1, -1))}${input.getValue(it + Point(1, 1))}" in mas &&
                        "${input.getValue(it + Point(-1, 1))}${input.getValue(it + Point(1, -1))}" in mas
            }
            .size
    }
}
