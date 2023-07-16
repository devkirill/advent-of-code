package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Matrix
import advent.calendar.aoc.solutions.utils.Point
import org.springframework.stereotype.Component

@Component
class Day17 : Solution<Matrix>(2021, 17) {
    fun parse(line: String, regex: Regex): IntRange {
        val match = regex.find(line)!!
        return match.groupValues[1].toInt()..match.groupValues[2].toInt()
    }

    override fun parse(lines: List<String>) = Matrix(
        parse(lines[0], Regex("x=(-?\\d+)\\.\\.(-?\\d+)")),
        parse(lines[0], Regex("y=(-?\\d+)\\.\\.(-?\\d+)"))
    )

    fun solves(input: Matrix) = Matrix(1..input.x.last, input.y.first..100)
        .indices
        .filter {
            var p = Point(0, 0)
            var shift = it
            while (p.x <= input.x.last && p.y >= input.y.first) {
                p += shift
                shift = Point(if (shift.x > 0) shift.x - 1 else 0, shift.y - 1)
                if (p in input) {
                    return@filter true
                }
            }
            false
        }

    override fun part1(input: Matrix): Int {
        val shift = solves(input).maxByOrNull { it.y }!!
        return shift.y * (shift.y + 1) / 2
    }

    override fun part2(input: Matrix): Int {
        return solves(input).size
    }
}
