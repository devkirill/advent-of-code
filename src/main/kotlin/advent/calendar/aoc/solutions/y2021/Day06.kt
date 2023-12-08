package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution

class Day06 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val l = input[0].split(",").map(String::toInt)
        var pos = (0..8).map { x -> l.filter { it == x }.size }
        repeat(80) {
            pos = (0..7).map { (if (it == 6) pos[0] else 0) + pos[it + 1] } + pos[0]
        }
        return pos.sum()
    }

    override fun part2(input: List<String>): Long {
        val l = input[0].split(",").map(String::toInt)
        var pos = (0..8).map { x -> l.filter { it == x }.size.toLong() }
        repeat(256) {
            pos = (0..7).map { (if (it == 6) pos[0] else 0) + pos[it + 1] } + pos[0]
        }
        return pos.sum()
    }
}
