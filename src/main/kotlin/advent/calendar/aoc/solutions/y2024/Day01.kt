package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.toCountMap
import kotlin.math.abs

class Day01 : Solution<List<List<Int>>>() {
    override fun parse(lines: List<String>) =
        lines.map { l -> l.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }

    override fun part1(input: List<List<Int>>): Int {
        val a = input.map { it[0] }.sorted()
        val b = input.map { it[1] }.sorted()
        return a.indices.sumOf { abs(b[it] - a[it]) }
    }

    override fun part2(input: List<List<Int>>): Int {
        val a = input.map { it[0] }.toCountMap()
        val b = input.map { it[1] }.toCountMap()
        return a.keys.sumOf { (a[it] ?: 0) * (b[it] ?: 0) * it }
    }
}
