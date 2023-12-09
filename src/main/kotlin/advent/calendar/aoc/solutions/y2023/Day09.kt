package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution

class Day09 : Solution<List<List<Long>>>() {
    override fun parse(lines: List<String>) = lines.map { it.split(" ").map { it.toLong() } }

    override fun part1(input: List<List<Long>>) = input.sumOf { line ->
        fun solve(line: List<Long>): Long {
            val s = step(line)
            if (s.all { it == 0L }) {
                return line.last()
            }
            return line.last() + solve(s)
        }

        solve(line)
    }

    override fun part2(input: List<List<Long>>) = input.sumOf { line ->
        fun solve(line: List<Long>): Long {
            val s = step(line)
            if (s.all { it == 0L }) {
                return line.first()
            }
            return line.first() - solve(s)
        }

        solve(line)
    }

    fun step(line: List<Long>) = (1 until line.size).map { line[it] - line[it - 1] }
}

