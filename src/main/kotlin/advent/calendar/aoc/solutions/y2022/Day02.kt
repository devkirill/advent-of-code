package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution

class Day02 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        return input.sumOf {
            val (a, b) = it.split(" ").map { p -> parse(p) }
            b + 1 + when {
                (a + 1) % 3 == b -> 6
                a == b -> 3
                else -> 0
            }
        }
    }

    override fun part2(input: List<String>): Int {
        return input.sumOf {
            val (a, b) = it.split(" ").map { p -> parse(p) }
            when (b) {
                0 -> 1 + (a + 2) % 3
                1 -> 1 + a % 3 + 3
                2 -> 1 + (a + 1) % 3 + 6
                else -> 0
            }
        }
    }

    fun parse(a: String) = if (a[0] < 'F') a[0] - 'A' else a[0] - 'X'
}
