package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.removeAt

class Day02 : Solution<List<List<Int>>>() {
    override fun parse(lines: List<String>) =
        lines.map { l -> l.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }

    override fun part1(input: List<List<Int>>): Int {
        return input.count { list -> isValid(list) || isValid(list.reversed()) }
    }

    override fun part2(input: List<List<Int>>): Int {
        return input.count { list -> isValid(list, true) || isValid(list.reversed(), true) }
    }

    fun isValid(seq: List<Int>, allowDrop: Boolean = false): Boolean {
        for (i in 1 until seq.size) {
            if (seq[i] - seq[i - 1] !in 1..3) {
                return allowDrop && (isValid(seq.removeAt(i - 1)) || isValid(seq.removeAt(i)))
            }
        }
        return true
    }
}
