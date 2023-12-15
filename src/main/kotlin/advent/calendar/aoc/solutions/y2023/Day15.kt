package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution

class Day15 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines.flatMap { it.split(",") }

    override fun part1(input: List<String>) = input.sumOf { line -> hash(line) }

    override fun part2(input: List<String>): Int {
        val data = (0..255).map { mutableMapOf<String, Int>() }
        for (command in input) {
            if ("-" in command) {
                val label = command.substringBefore("-")
                data[hash(label)].remove(label)
            }
            if ("=" in command) {
                val (label, num) = command.split("=")
                data[hash(label)][label] = num.toInt()
            }
        }
        return data.mapIndexed { i, m -> m.keys.mapIndexed { j, k -> (i + 1) * (j + 1) * m[k]!! }.sum() }.sum()
    }

    fun hash(s: String): Int {
        var res = 0
        for (c in s) {
            res += c.toInt()
            res *= 17
            res %= 256
        }
        return res
    }
}
