package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day08 : Solution<Day08.Data>() {
    override fun parse(lines: List<String>) = Data(lines[0], lines.drop(2).associate { line ->
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

    data class Data(val instruction: String, val moves: Map<String, Pair<String, String>>)
}

