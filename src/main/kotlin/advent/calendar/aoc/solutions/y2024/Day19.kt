package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.splitEmptyLine
import advent.calendar.aoc.solutions.y2024.Day19.Data

class Day19 : Solution<Data>() {
    override fun parse(lines: List<String>) = lines.splitEmptyLine().let { (a, b) ->
        val a = a.map { it.split(", ").toSet() }.reduce { a, b -> a + b }
        Data(a.map { it.split(", ").toSet() }.reduce { a, b -> a + b }, b)
    }

    fun solve(towels: Set<String>, design: String): Long {
        val map = mutableMapOf<Int, Long>(0 to 1L)
        for (i in 0 until design.length) {
            val c = map[i] ?: 0
            if (c > 0L) {
                val str = design.substring(i)
                val valid = towels.filter { str.startsWith(it) }.map { it.length }.distinct()
                for (v in valid) {
                    map[v + i] = (map[v + i] ?: 0L) + c
                }
            }
        }
        return map[design.length] ?: 0L
    }

    override fun part1(input: Data) = input.designs.count { solve(input.towels, it) != 0L }

    override fun part2(input: Data) = input.designs.sumOf { solve(input.towels, it) }

    data class Data(
        val towels: Set<String>,
        val designs: List<String>
    )
}
