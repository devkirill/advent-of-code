package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.toInt
import org.springframework.stereotype.Component

typealias Type = List<List<IntRange>>

@Component
class Day04 : Solution<Type>(2022, 4) {
    override fun parse(lines: List<String>) = lines.map {
        it.split(",").map { val (a, b) = it.split("-").toInt(); a..b }
    }

    override fun part1(input: Type): Int {
        return input.count { it[0] in it[1] || it[1] in it[0] }
    }

    override fun part2(input: Type): Int {
        return input.count { it[0] intersect it[1] }
    }
}

operator fun IntRange.contains(range: IntRange) = range.first in this && range.last in this
infix fun IntRange.intersect(range: IntRange) = range.first <= this.last && range.last >= this.first