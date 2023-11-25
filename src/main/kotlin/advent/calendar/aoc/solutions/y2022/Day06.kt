package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day06 : Solution<String>() {
    override fun parse(lines: List<String>): String = lines.single()

    override fun part1(input: String): Int {
        for (i in input.indices) {
            val str = input.substring(i, i + 4)
            if (str.toSet().size == 4) {
                return i + 4
            }
        }
        return 0
    }
    override fun part2(input: String): Int {
        for (i in input.indices) {
            val str = input.substring(i, i + 14)
            if (str.toSet().size == 14) {
                return i + 14
            }
        }
        return 0
    }
}
