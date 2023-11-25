package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day01 : Solution<List<Int>>() {
    override fun parse(lines: List<String>): List<Int> {
        var result = listOf<Int>()
        var cur = 0
        lines.forEach {
            if (it == "") {
                result = result + cur
                cur = 0
            } else {
                cur += it.toInt()
            }
        }
        result = result + cur
        return result
    }

    override fun part1(input: List<Int>): Int {
        return input.maxOf { it }
    }

    override fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }
}
