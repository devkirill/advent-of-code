package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class Day07 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val l = input[0].split(",").map(String::toInt)
        return (0..1000).map { p -> l.sumOf { abs(it - p) } }.minOf { it }
    }

    override fun part2(input: List<String>): Int {
        val l = input[0].split(",").map(String::toInt)
        return (0..1000).map { p -> l.sumOf { abs(it - p) * (abs(it - p) + 1) / 2 } }.minOf { it }
    }
}
