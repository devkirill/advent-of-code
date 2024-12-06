package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby4Diag
import advent.calendar.aoc.solutions.utils.geom.nearby8

class Day04 : Solution<Grid>() {
    override fun parse(lines: List<String>) = Grid(lines) { "." }

    override fun part1(input: Grid): Int {
        val shifts = Point(0, 0).nearby8()
        return input.keys
            .flatMap { p ->
                shifts.map { s -> (0 until 4).joinToString("") { input[p + s * it] } }
            }
            .filter { it == "XMAS" }
            .size
    }

    override fun part2(input: Grid): Any {
        return input.keys
            .filter { x ->
                input[x] == "A" && Point(0, 0).nearby4Diag().joinToString("") { input[x + it] } in listOf(
                    "MMSS",
                    "MSSM",
                    "SSMM",
                    "SMMS"
                )
            }
            .size
    }
}
