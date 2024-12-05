package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby4Diag
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
        return input.keys
            .filter { x ->
                input.getValue(x) == 'A' && Point(0, 0).nearby4Diag().map { input.getValue(x + it) }
                    .joinToString("") in listOf("MMSS", "MSSM", "SSMM", "SMMS")
            }
            .size
    }
}
