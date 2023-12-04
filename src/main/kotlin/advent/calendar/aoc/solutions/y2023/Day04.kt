package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.parseInt
import advent.calendar.aoc.solutions.utils.pow
import org.springframework.stereotype.Component

@Component
class Day04 : Solution<List<Day04.Card>>() {
    override fun parse(lines: List<String>) = lines.map { line ->
        val (c, a) = line.split(": ")
        val (l, r) = a.split(" | ")
        Card(
            parseInt(c),
            l.split(" ").filter { it.isNotBlank() }.map { it.toInt() },
            r.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        )
    }

    override fun part1(input: List<Card>) = input.sumOf { card ->
        val c = card.list.count { it in card.winners }
        pow(2, c - 1)
    }

    override fun part2(input: List<Card>): Any {
        val a = input.map { card -> card.list.count { it in card.winners } }
        val counts = input.map { 1L }.toMutableList()
        for (x in a.indices) {
            for (i in x + 1..x + a[x]) {
                if (i in a.indices) {
                    counts[i] += counts[x]
                }
            }
        }
        return counts.reduce { a, b -> a + b }
    }

    data class Card(val id: Int, val winners: List<Int>, val list: List<Int>)
}
