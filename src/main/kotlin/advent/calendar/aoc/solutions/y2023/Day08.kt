package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.lcm

class Day08 : Solution<Day08.Data>() {
    override fun parse(lines: List<String>) = Data(lines[0], lines.drop(2)
        .associate { line ->
            val mr = Regex("(.*) = \\((.*), (.*)\\)").matchEntire(line)!!
            val (a, b, c) = mr.groups.map { it!!.value }.drop(1)
            a to (b to c)
        }
    )

    override fun part1(input: Data): Int {
        var result = 0
        var i = 0
        var state = "AAA"
        while (state != "ZZZ") {
            val (l, r) = input.moves[state]!!
            val next = if (input.instruction[i] == 'L') l else r
            i = (i + 1) % input.instruction.length
            state = next
            result++
        }
        return result
    }

    override fun part2(input: Data): Long {
        val state = input.moves.keys.filter { it.endsWith("A") }
        val circles = state.map { it to circle(input, it) }
        return circles.flatMap { it.second }.map { it.third.toLong() }.reduce { a, b -> lcm(a, b) }
    }

    fun circle(input: Data, from: String): List<Triple<String, Int, Int>> {
        var step = 0
        var i = 0
        val result = mutableListOf<Triple<String, Int, Int>>()
        var state = from
        while (true) {
            val c = input.instruction[i]
            val (l, r) = input.moves[state]!!
            state = if (c == 'L') l else r
            i = (i + 1) % input.instruction.length
            step++
            if (state[2] == 'Z') {
                val t = Triple(state, i, step)
                if (result.any { (a, b, c) -> a == t.first && b == t.second }) {
                    break
                }
                result += t
            }
        }
        return result
    }

    data class Data(val instruction: String, val moves: Map<String, Pair<String, String>>)
}

