package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import kotlin.math.max

class Day02 : Solution<List<Day02.Game>>() {
    override fun parse(lines: List<String>) = lines.map { line ->
        val (a, b) = line.split(": ")
        val maps = b.split("; ").map { g -> g.split(", ").map { val (n, c) = it.split(" "); c to n.toInt() }.toMap() }
        val id = a.split(" ").last().toInt()
        Game(id, maps)
    }

    override fun part1(input: List<Game>) = input.filter { g ->
        g.states.all {
            (it["red"] ?: 0) <= 12 && (it["green"] ?: 0) <= 13 && (it["blue"] ?: 0) <= 14
        }
    }.sumOf { it.id }

    override fun part2(input: List<Game>) = input.sumOf { g ->
        val state = g.states.reduce { a, b -> (a.keys + b.keys).toSet().associateWith { max(a[it] ?: 0, b[it] ?: 0) } }
        setOf("red", "green", "blue").map { state[it] ?: 0 }.reduce { a, b -> a * b }
    }

    data class Game(val id: Int, val states: List<Map<String, Int>>)
}
