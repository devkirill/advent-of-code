package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution

class Day03 : Solution<String>() {
    override fun parse(lines: List<String>) = lines.joinToString("\n")

    override fun part1(input: String): Long {
        return Regex("mul\\(\\d+,\\d+\\)").findAll(input).map { it.value }
            .map { it.removePrefix("mul(").removeSuffix(")") }
            .map { it.split(",").map { it.toLong() }.reduce { a, b -> a * b } }
            .sum()
    }

    override fun part2(input: String): Long {
        return Regex("mul\\(\\d+,\\d+\\)|don.t\\(\\)|do\\(\\)").findAll(input)
            .map { it.value }
            .fold(0L to true) { (cur, flag), b ->
                when {
                    b.startsWith("mul") && flag -> {
                        val bb = b.removePrefix("mul(").removeSuffix(")").split(",").map { it.toLong() }
                            .reduce { a, b -> a * b }
                        (cur + bb) to flag
                    }

                    b.startsWith("don't") -> {
                        cur to false
                    }

                    b.startsWith("do") -> {
                        cur to true
                    }

                    else -> cur to flag
                }
            }
            .first
    }
}
