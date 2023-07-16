package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day01 : Solution<List<Int>>(2021, 1) {
    override fun parse(lines: List<String>) = lines.map { it.toInt() }

    override fun part1(input: List<Int>): Int {
        var result = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i - 1]) {
                result++
            }
        }
        return result
    }

    override fun part2(input: List<Int>): Int {
        var result = 0
        for (i in 3 until input.size) {
            if (input[i] > input[i - 3]) {
                result++
            }
        }
        return result
    }
}
