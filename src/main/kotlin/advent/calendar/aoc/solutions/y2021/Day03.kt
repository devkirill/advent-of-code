package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.middle

class Day03 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val len = input[0].length
        fun popular(input: List<String>): String {
            var result = ""
            for (i in 0 until len) {
                result += input.map { it[i] }.sorted().middle().toString()
            }
            return result
        }

        val a = popular(input).toInt(2)
        val b = a xor "1".repeat(len).toInt(2)
        return a * b
    }

    override fun part2(input: List<String>): Int {
        val len = input[0].length
        fun popular(input: List<String>): String {
            var pop = input
            for (i in 0 until len) {
                val a = pop.filter { it[i] == '0' }
                val b = pop.filter { it[i] == '1' }
                pop = if (a.size <= b.size) b else a
            }
            return pop.first()
        }

        fun nonPopular(input: List<String>): String {
            var pop = input
            for (i in 0 until len) {
                val a = pop.filter { it[i] == '0' }
                val b = pop.filter { it[i] == '1' }
                if (a.isNotEmpty() && b.isNotEmpty()) {
                    pop = if (a.size <= b.size) a else b
                }
            }
            return pop.first()
        }

        val a = popular(input).toInt(2)
        val b = nonPopular(input).toInt(2)
        return a * b
    }
}
