package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day03 : Solution<List<String>>(2022, 3) {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        return input.sumOf {
            val a = it.substring(0 until it.length / 2)
            val b = it.substring(it.length / 2)
            val inter = a.toSet().intersect(b.toSet())
            inter.sumOf { toNum(it) }
        }
    }

    override fun part2(input: List<String>): Int {
        return input.chunked(6).sumOf { list ->
            val (a, b) = list.chunked(3)
            val x = a.map { it.toSet() }.reduce { x, y -> x.intersect(y) }
            val y = b.map { it.toSet() }.reduce { x, y -> x.intersect(y) }
            x.sumOf { toNum(it) } + y.sumOf { toNum(it) }
        }
    }

    fun toNum(c: Char) = if (c in 'a'..'z') c - 'a' + 1 else c - 'A' + 27
}
